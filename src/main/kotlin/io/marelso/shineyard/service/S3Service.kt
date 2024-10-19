package io.marelso.shineyard.service

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.AmazonS3URI
import com.amazonaws.services.s3.model.ObjectMetadata
import io.marelso.shineyard.utils.FileUtils.Companion.normalizeFileName
import jakarta.annotation.PostConstruct
import org.apache.commons.fileupload.FileUploadException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class S3Service {
    private var s3: AmazonS3? = null

    @Value("\${aws.s3.bucketName}")
    private val bucket: String? = null

    @Value("\${aws.s3.accessKey}")
    private val accessKey: String? = null

    @Value("\${aws.s3.secretKey}")
    private val secretKey: String? = null

    private val UPLOAD_FOLDER = "uploads"

    private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss")

    @PostConstruct
    private fun initialize() {
        val awsCredentials = BasicAWSCredentials(accessKey, secretKey)
        s3 = AmazonS3ClientBuilder.standard()
            .withCredentials(AWSStaticCredentialsProvider(awsCredentials))
            .withRegion(Regions.US_EAST_1)
            .build()
    }

    fun uploadFile(multipartFile: MultipartFile): String {
        try {
            val objectMetadata = ObjectMetadata().apply {
                contentType = multipartFile.contentType
                contentLength = multipartFile.size
            }
            val fileName = "$UPLOAD_FOLDER/${multipartFile.originalFilename}"

            s3?.putObject(
                bucket,
                fileName,
                multipartFile.inputStream,
                objectMetadata
            )

            return s3?.getUrl(bucket, fileName).toString()
        } catch (e: IOException) {
            throw FileUploadException("Error occurred in file upload ==> " + e.message)
        }
    }

    private fun metadata(file: MultipartFile, fileName: String): ObjectMetadata {
        val metadata = ObjectMetadata()
        metadata.contentLength = file.size
        metadata.addUserMetadata("x-amz-meta-title", fileName)
        return metadata
    }

    private fun generateFileName(file: MultipartFile): String {
        val normalized: String = normalizeFileName(file.originalFilename)
        val uuid = UUID.randomUUID().toString()
        return uuid + "_" + normalized
    }

    fun deleteImage(imageUrl: String) {
        val s3Uri = getUriFromUrl(imageUrl)

        s3Uri.ifPresentOrElse(
            { uri: AmazonS3URI ->
                s3!!.deleteObject(uri.bucket, uri.key)
            },
            { throw RuntimeException("Error deleting image $imageUrl") }
        )
    }

    private fun getUriFromUrl(imageUrl: String): Optional<AmazonS3URI> {
        return try {
            Optional.of(AmazonS3URI(imageUrl))
        } catch (e: IllegalArgumentException) {
            Optional.empty()
        }
    }
}