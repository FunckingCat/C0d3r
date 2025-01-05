package ru.davidzh.coder.backend.dao.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.davidzh.coder.backend.dao.entity.ExecutionResultEntity

@Repository
interface ExecutionResultRepository: CrudRepository<ExecutionResultEntity, Long>