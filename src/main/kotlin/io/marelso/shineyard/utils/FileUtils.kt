package io.marelso.shineyard.utils

import org.apache.commons.lang3.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.io.InputStream

class FileUtils {
    companion object {
        fun getInputStream(file: MultipartFile): InputStream {
            try {
                return file.inputStream
            } catch (exception: IOException) {
                throw RuntimeException("Error while trying to get file input stream")
            }
        }

        fun normalizeFileName(target: String?): String {
            return StringUtils.stripAccents(target).replace("[^a-zA-Z0-9\\.\\-]".toRegex(), "_")
        }
    }
}