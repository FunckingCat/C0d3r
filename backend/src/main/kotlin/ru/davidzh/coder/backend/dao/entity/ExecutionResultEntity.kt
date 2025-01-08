package ru.davidzh.coder.backend.dao.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import ru.davidzh.coder.backend.model.ExecutionStatus
import java.time.LocalDateTime

/**
 * Entity class representing the execution result of a job.
 *
 * This class stores the details of a job execution result, including the job ID, execution start and finish times,
 * status, logs, exit code, error messages, and other related information.
 */
@Table("execution_results")
data class ExecutionResultEntity(
    /**
     * Identifier of the execution result.
     */
    @Id val id: Long? = null,

    /**
     * Identifier of the job associated with this execution result.
     */
    val jobId: Long,

    /**
     * Start time of the job execution.
     */
    val startedAt: LocalDateTime,

    /**
     * Finish time of the job execution. Can be null if the job is still running.
     */
    val finishedAt: LocalDateTime? = null,

    /**
     * Status of the job execution, as defined by the [ExecutionStatus] enum.
     */
    val status: ExecutionStatus,

    /**
     * Logs generated during the job execution, if any.
     */
    val logs: List<String>? = null,

    /**
     * Exit code of the process, typically 0 for a successful completion.
     */
    val exitCode: Int? = 0,

    /**
     * Error message encountered during job execution, if any.
     */
    val errorMessage: String? = null,

    /**
     * Date and time when the execution result was recorded in the database.
     */
    val recordedAt: LocalDateTime,

    /**
     * Original name of the job that generated this execution result.
     */
    val originalJobName: String? = null
)