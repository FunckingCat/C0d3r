package ru.davidzh.coder.backend.converter

import org.mapstruct.Mapper
import ru.davidzh.coder.backend.dao.entity.ExecutionResultEntity
import ru.davidzh.coder.backend.model.ExecutionResult

@Mapper(componentModel = "spring")
abstract class ExecutionResultConverter {

    abstract fun convert(entity: ExecutionResultEntity): ExecutionResult

}