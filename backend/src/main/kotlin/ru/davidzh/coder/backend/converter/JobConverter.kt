package ru.davidzh.coder.backend.converter

import com.fasterxml.jackson.core.type.TypeReference
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import ru.davidzh.coder.backend.dao.entity.JobEntity
import ru.davidzh.coder.backend.model.Job
import ru.davidzh.coder.backend.util.ObjectMapperProvider.MAPPER

/**
 * Converter class to map [Job].
 *
 * This class is responsible for converting
 * to the domain model [Job].
 */
@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = [
        ExecutionResultConverter::class
    ]
)
abstract class JobConverter {

    /**
     * Converts a [JobEntity] to a [Job] domain model.
     *
     * This method maps the data from a persistent [JobEntity] into the corresponding
     * business model [Job], which may include transforming database-specific fields
     * into application-specific data structures.
     *
     * @param jobEntity the [JobEntity] instance to convert.
     * @return the converted [Job] domain model.
     */
    abstract fun convert(jobEntity: JobEntity): Job

    /**
     * Converts a string representing a map into an actual [Map].
     *
     * @param map the string representation of the map.
     * @return the [Map] created from the string.
     */
    fun stringToMap(map: String): Map<String, String> = if (map.isEmpty())
        emptyMap() else MAPPER.readValue(map, object : TypeReference<Map<String, String>>() {})

}