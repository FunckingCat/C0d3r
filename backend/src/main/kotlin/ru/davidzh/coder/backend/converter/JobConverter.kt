package ru.davidzh.coder.backend.converter

import com.fasterxml.jackson.core.type.TypeReference
import org.mapstruct.Mapper
import ru.davidzh.coder.backend.dao.entity.JobEntity
import ru.davidzh.coder.backend.model.Job
import ru.davidzh.coder.backend.util.ObjectMapperProvider.MAPPER

@Mapper(componentModel = "spring")
abstract class JobConverter {

    abstract fun convert(jobEntity: JobEntity): Job

    fun stringToMap(map: String): Map<String, String> = if (map.isEmpty())
        emptyMap() else MAPPER.readValue(map, object : TypeReference<Map<String, String>>() {})

}