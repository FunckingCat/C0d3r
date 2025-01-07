package ru.davidzh.coder.backend.converter

import com.fasterxml.jackson.core.type.TypeReference
import org.mapstruct.Mapper
import ru.davidzh.coder.backend.controller.dto.CreateJobRequest
import ru.davidzh.coder.backend.dao.entity.JobEntity
import ru.davidzh.coder.backend.model.JobParameters
import ru.davidzh.coder.backend.util.ObjectMapperProvider.MAPPER
import java.util.*

@Mapper(componentModel = "spring")
abstract class JobParametersConverter {

    abstract fun convert(createJobRequest: CreateJobRequest, userId: UUID): JobParameters

    abstract fun convert(jobEntity: JobEntity): JobParameters

    fun stringToMap(map: String): Map<String, String> = if (map.isEmpty())
        emptyMap() else MAPPER.readValue(map, object : TypeReference<Map<String, String>>() {})

}