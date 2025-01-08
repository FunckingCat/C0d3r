package ru.davidzh.coder.backend.converter

import org.mapstruct.Mapper
import ru.davidzh.coder.backend.dao.entity.ExecutionResultEntity
import ru.davidzh.coder.backend.model.ExecutionResult

/**
 * Converter class to map  [ExecutionResultEntity].
 *
 * This class is responsible for converting
 * to the corresponding database entity [ExecutionResultEntity].
 */
@Mapper(componentModel = "spring")
abstract class ExecutionResultEntityConverter {

    /**
     * Converts an [ExecutionResult] to an [ExecutionResultEntity].
     *
     * @param entity the [ExecutionResult] to be converted.
     * @return the converted [ExecutionResultEntity] instance.
     */
    abstract fun convert(entity: ExecutionResult): ExecutionResultEntity

}