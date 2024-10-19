package io.marelso.shineyard.config

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {
    @Bean
    @ConditionalOnMissingBean(AmazonS3::class)
    fun s3(): AmazonS3 = AmazonS3ClientBuilder.standard()
        .withPathStyleAccessEnabled(true)
        .build()
}