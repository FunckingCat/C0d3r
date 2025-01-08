package ru.davidzh.coder.backend.converter

import com.fasterxml.jackson.core.type.TypeReference
import org.mapstruct.Mapper
import ru.davidzh.coder.backend.controller.dto.CreateJobRequest
import ru.davidzh.coder.backend.dao.entity.JobEntity
import ru.davidzh.coder.backend.model.JobParameters
import ru.davidzh.coder.backend.util.ObjectMapperProvider.MAPPER
import java.util.*

/**
 * Converter class to map to [JobParameters].
 *
 * This class is responsible for converting
 * to the corresponding [JobParameters] domain model.
 */
@Mapper(componentModel = "spring")
abstract class JobParametersConverter {

    /**
     * Converts a [CreateJobRequest] and [userId] to [JobParameters].
     *
     * This method maps a [CreateJobRequest] into the corresponding [JobParameters] domain model,
     * using the provided [userId] for further data enrichment.
     *
     * @param createJobRequest the job creation request to convert.
     * @param userId the ID of the user initiating the job creation.
     * @return the converted [JobParameters] domain model.
     */
    abstract fun convert(createJobRequest: CreateJobRequest, userId: UUID): JobParameters

    /**
     * Converts a [JobEntity] to [JobParameters].
     *
     * This method maps a [JobEntity] from the database into the corresponding [JobParameters] domain model.
     *
     * @param jobEntity the [JobEntity] instance to convert.
     * @return the converted [JobParameters] domain model.
     */
    abstract fun convert(jobEntity: JobEntity): JobParameters

    /**
     * Converts a JSON string to a [Map<String, String>].
     *
     * This method deserializes a JSON string into a [Map<String, String>].
     *
     * @param map the JSON string representing the map to convert.
     * @return the deserialized [Map<String, String>].
     */
    fun stringToMap(map: String): Map<String, String> = if (map.isEmpty())
        emptyMap() else MAPPER.readValue(map, object : TypeReference<Map<String, String>>() {})

}