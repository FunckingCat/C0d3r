package ru.davidzh.coder.backend.dao.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.davidzh.coder.backend.dao.entity.JobEntity
import ru.davidzh.coder.backend.model.JobStatus
import java.util.*

/**
 * Repository interface for performing CRUD operations on the [JobEntity] class.
 *
 * This interface provides methods for accessing and manipulating job data from the database.
 * It extends [CrudRepository], offering basic CRUD functionalities for the [JobEntity].
 */
@Repository
interface JobRepository: CrudRepository<JobEntity, Long> {

    /**
     * Finds all jobs belonging to the given user ID.
     *
     * @param userId the ID of the user whose jobs are to be retrieved.
     * @return a list of jobs associated with the user.
     */
    fun findAllByUserId(userId: UUID): List<JobEntity>

    /**
     * Finds all jobs belonging to the given group ID.
     *
     * @param groupId the ID of the user whose jobs are to be retrieved.
     * @return a list of jobs associated with the user.
     */
    fun findAllByGroupId(groupId: UUID): List<JobEntity>

    /**
     * Finds all jobs with the specified status.
     *
     * @param status the status of the jobs to find, as defined by the {@link JobStatus} enum.
     * @return a list of {@link JobEntity} instances that have the specified status.
     *         If no jobs are found, returns an empty list.
     */
    fun findByStatus(status: JobStatus): List<JobEntity>

}