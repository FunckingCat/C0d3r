package ru.davidzh.coder.backend.converter

import org.mapstruct.Mapper
import ru.davidzh.coder.backend.controller.dto.CreateJobRequest
import ru.davidzh.coder.backend.model.JobParameters
import java.util.*

@Mapper(componentModel = "spring")
interface JobParametersConverter {

    fun convert(createJobRequest: CreateJobRequest, userId: UUID): JobParameters

}