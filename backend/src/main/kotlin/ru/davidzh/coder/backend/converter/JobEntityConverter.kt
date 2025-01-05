package ru.davidzh.coder.backend.converter

import org.mapstruct.Mapper
import ru.davidzh.coder.backend.dao.entity.JobEntity
import ru.davidzh.coder.backend.model.JobParameters
import ru.davidzh.coder.backend.util.ObjectMapperProvider.MAPPER

@Mapper(componentModel = "spring")
interface JobEntityConverter {

    fun convert(jobParameters: JobParameters): JobEntity

    fun mapToString(map: Map<String, String>): String = MAPPER.writeValueAsString(map)

}