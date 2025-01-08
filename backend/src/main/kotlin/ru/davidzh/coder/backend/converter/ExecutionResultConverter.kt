package ru.davidzh.coder.backend.converter

import org.mapstruct.Mapper
import ru.davidzh.coder.backend.dao.entity.ExecutionResultEntity
import ru.davidzh.coder.backend.model.ExecutionResult

/**
 * Converter class to map [ExecutionResult].
 *
 * This class is used to convert the [ExecutionResult].
 */
@Mapper(componentModel = "spring")
abstract class ExecutionResultConverter {

    /**
     * Converts an [ExecutionResultEntity] to an [ExecutionResult].
     *
     * @param entity the [ExecutionResultEntity] to be converted.
     * @return the converted [ExecutionResult] instance.
     */
    abstract fun convert(entity: ExecutionResultEntity): ExecutionResult

}