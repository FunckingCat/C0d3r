package ru.davidzh.coder.backend.dao.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.davidzh.coder.backend.dao.entity.ExecutionResultEntity
import ru.davidzh.coder.backend.model.ExecutionStatus

@Repository
interface ExecutionResultRepository: CrudRepository<ExecutionResultEntity, Long> {

    fun findByStatus(status: ExecutionStatus): List<ExecutionResultEntity>

}