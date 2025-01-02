package ru.davidzh.coder.backend.converter

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import ru.davidzh.coder.backend.controller.dto.CreateJobRequest
import ru.davidzh.coder.backend.model.Job

@Mapper(componentModel = "spring")
interface JobConverter {

    @Mapping(target = "status", constant = "PENDING")
    fun convert(createJobRequest: CreateJobRequest): Job

}