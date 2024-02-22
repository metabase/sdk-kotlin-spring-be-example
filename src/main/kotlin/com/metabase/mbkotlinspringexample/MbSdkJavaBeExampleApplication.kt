@file:JvmName("sdk-backend")

package com.metabase.mbkotlinspringexample

import com.metabase.mbkotlinspringexample.util.AuthUtil
import com.metabase.mbkotlinspringexample.util.JwtUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class WebConfig(@Value("\${client.site.url}") private val clientSiteUrl: String) : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins(clientSiteUrl)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true)
    }
}

@SpringBootApplication
class MbSdkJavaBeExampleApplication {

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }

    @Bean
    fun jwtUtil(): JwtUtil {
        return JwtUtil()
    }

    @Bean
    fun authUtil() : AuthUtil {
        val encoder = BCryptPasswordEncoder()
        return AuthUtil(encoder)
    }
}

fun main(args: Array<String>) {
    runApplication<MbSdkJavaBeExampleApplication>(*args)
}

