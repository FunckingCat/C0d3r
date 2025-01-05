package ru.davidzh.coder.backend.dao.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.davidzh.coder.backend.dao.entity.JobEntity
import java.util.*

@Repository
interface JobRepository: CrudRepository<JobEntity, Long> {

    /**
     * Finds all jobs belonging to the given user ID.
     *
     * @param userId the ID of the user whose jobs are to be retrieved.
     * @return a list of jobs associated with the user.
     */
    fun findAllByUserId(userId: UUID): List<JobEntity>

}