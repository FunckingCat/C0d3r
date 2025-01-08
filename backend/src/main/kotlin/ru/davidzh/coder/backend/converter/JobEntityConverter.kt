package ru.davidzh.coder.backend.converter

import org.mapstruct.Mapper
import ru.davidzh.coder.backend.dao.entity.JobEntity
import ru.davidzh.coder.backend.model.JobParameters
import ru.davidzh.coder.backend.util.ObjectMapperProvider.MAPPER

/**
 * Converter class to map [JobEntity].
 *
 * This class is responsible for converting
 * into a database entity [JobEntity].
 */
@Mapper(componentModel = "spring")
abstract class JobEntityConverter {

    /**
     * Converts a [JobParameters] domain model to a [JobEntity].
     *
     * This method maps the business model [JobParameters] into a database entity [JobEntity],
     * including any necessary transformations of data to fit the entity structure.
     *
     * @param jobParameters the [JobParameters] instance to convert.
     * @return the converted [JobEntity] database entity.
     */
    abstract fun convert(jobParameters: JobParameters): JobEntity

    /**
     * Converts a map of strings to a JSON string representation.
     *
     * This method serializes a [Map<String, String>] into a JSON string.
     *
     * @param map the [Map<String, String>] to convert into a JSON string.
     * @return the JSON string representation of the provided map.
     */
    fun mapToString(map: Map<String, String>): String = MAPPER.writeValueAsString(map)

}