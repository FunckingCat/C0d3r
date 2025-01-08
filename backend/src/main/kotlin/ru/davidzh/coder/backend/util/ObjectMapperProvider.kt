package ru.davidzh.coder.backend.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule

/**
 * A provider for an [ObjectMapper] instance configured for custom serialization.
 *
 * This object provides a pre-configured [ObjectMapper] instance that can be used
 * across the application for JSON serialization and deserialization. The configured
 * [ObjectMapper] includes support for Java 8 time types and Kotlin-specific features.
 */
object ObjectMapperProvider {

    /**
     * A globally configured instance of [ObjectMapper] for serializing and deserializing objects.
     *
     * This [ObjectMapper] is set up with the following configurations:
     * - JavaTimeModule to handle Java 8 date and time types (e.g., LocalDate, LocalDateTime).
     * - KotlinModule to support Kotlin-specific features, like data classes.
     * - Configuration to prevent failures when encountering empty beans (SerializationFeature.FAIL_ON_EMPTY_BEANS).
     */
    val MAPPER: ObjectMapper = ObjectMapper()
        .registerModule(JavaTimeModule())
        .registerModule(KotlinModule.Builder().build())
        .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)

}