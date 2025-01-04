package ru.davidzh.coder.backend.dao.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.davidzh.coder.backend.dao.model.JobEntity

@Repository
interface JobRepository: CrudRepository<JobEntity, Long>