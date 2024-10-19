package io.marelso.shineyard.controller

import io.marelso.shineyard.service.S3Service
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/auth/img")
class FileController(private val s3Service: S3Service) {
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @Operation(summary = "Upload an image to the yard' image repository.")
    fun uploadFile(@RequestParam("file") file: MultipartFile): String =  s3Service.uploadFile(file)
}