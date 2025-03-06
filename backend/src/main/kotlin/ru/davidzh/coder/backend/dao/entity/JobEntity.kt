package ru.davidzh.coder.backend.dao.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.MappedCollection
import org.springframework.data.relational.core.mapping.Table
import ru.davidzh.coder.backend.model.ExecutionType
import ru.davidzh.coder.backend.model.JobStatus
import java.time.LocalDateTime
import java.util.*

/**
 * Entity class representing a job in the system.
 *
 * This class holds information related to a job, including its unique identifier, user ID, job name, creation time,
 * Docker image details, execution command, environment variables, execution type, status, and related execution results.
 */
@Table("jobs")
data class JobEntity(
    /**
     * Unique identifier of the job entity in the database.
     */
    @Id val id: Long? = null,

    /**
     * Unique user identifier (UUID) of the user to whom the job belongs.
     */
    val userId: UUID,

    /**
     * Unique user identifier (UUID) of the group the job belongs.
     */
    val groupId: UUID? = null,

    /**
     * Name of the job.
     */
    val name: String,

    /**
     * Creation time of the job.
     */
    var createdAt: LocalDateTime?,

    /**
     * Docker image URL or local .tar file path used for the job execution.
     */
    val dockerImage: String,

    /**
     * Command to be executed when running the job in the container.
     */
    val command: List<String>,

    /**
     * Environment variables to be set when the container is started.
     */
    val environmentVariables: String,

    /**
     * The execution type of the job (e.g., manual, scheduled, etc.).
     */
    val executionType: ExecutionType,

    /**
     * CronJob schedule if the job is executed by a CronJob.
     */
    val schedule: String? = null,

    /**
     * Status of the job (e.g., running, completed, failed).
     */
    val status: JobStatus? = null,

    /**
     * The ordinal number of the current job execution.
     */
    var ordinal: Int? = null,

    /**
     * Flag indicating whether the job has been deleted.
     */
    var deleted: Boolean? = false,

    /**
     * List of execution results associated with this job.
     */
    @MappedCollection(idColumn = "job_id", keyColumn = "id")
    val executionResults: List<ExecutionResultEntity>? = emptyList(),
)