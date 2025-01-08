package ru.davidzh.coder.backend.dao.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.davidzh.coder.backend.dao.entity.ExecutionResultEntity
import ru.davidzh.coder.backend.model.ExecutionStatus

/**
 * Repository interface for performing CRUD operations on the [ExecutionResultEntity] class.
 *
 * This interface provides methods for accessing and manipulating execution result data from the database.
 * It extends [CrudRepository], providing basic CRUD functionalities for the [ExecutionResultEntity].
 */
@Repository
interface ExecutionResultRepository : CrudRepository<ExecutionResultEntity, Long> {

    /**
     * Finds all execution results with the specified status.
     *
     * @param status the status of the execution results to find, as defined by the {@link ExecutionStatus} enum.
     * @return a list of {@link ExecutionResultEntity} instances with the specified status.
     *         If no results are found, returns an empty list.
     */
    fun findByStatus(status: ExecutionStatus): List<ExecutionResultEntity>

    /**
     * Finds all execution results associated with the specified job ID.
     *
     * @param jobId the ID of the job whose execution results are to be retrieved.
     * @return a list of {@link ExecutionResultEntity} instances associated with the specified job ID.
     *         If no results are found, returns an empty list.
     */
    fun findByJobId(jobId: Long): List<ExecutionResultEntity>

    /**
     * Finds a single execution result by the original job name.
     *
     * @param originalJobName the original name of the job whose execution result is to be retrieved.
     * @return the {@link ExecutionResultEntity} instance associated with the specified original job name,
     *         or null if no result is found.
     */
    fun findByOriginalJobName(originalJobName: String): ExecutionResultEntity?
}
