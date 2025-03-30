package ru.davidzh.coder.backend.model

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "Represents the outcome and details of a single execution attempt for a specific job.")
data class ExecutionResult(

    @field:Schema(
        description = "Unique identifier for this specific execution instance.",
        example = "54321"
    )
    val id: Long? = null,

    @field:Schema(
        description = "Identifier of the job this execution belongs to.",
        example = "101"
    )
    val jobId: Long,

    @field:Schema(
        description = "Timestamp indicating when this execution attempt started.",
        example = "2023-10-27T14:00:05",
        format = "date-time"
    )
    val startedAt: LocalDateTime,

    @field:Schema(
        description = "Timestamp indicating when this execution attempt finished (successfully or otherwise). Null if still running.",
        example = "2023-10-27T14:05:15",
        format = "date-time"
    )
    val finishedAt: LocalDateTime? = null,

    @field:Schema(
        description = "The final status of this execution attempt (e.g., SUCCEEDED, FAILED).",
        implementation = ExecutionStatus::class

    )
    val status: ExecutionStatus,

    @field:ArraySchema(
        schema = Schema(description = "A line of log output from the execution.", example = "[INFO] Processing record 123...")
    )
    @field:Schema(
        description = "A list containing the log output (stdout/stderr) captured during execution. May be truncated or sampled.",
    )
    val logs: List<String>? = null,

    @field:Schema(
        description = "The exit code returned by the main process of the job container (e.g., 0 indicates success, non-zero often indicates failure). Null if not applicable or not yet finished.",
        example = "0"
    )
    val exitCode: Int?,

    @field:Schema(
        description = "Any error message captured during execution, particularly if the status is FAILED. Null if execution was successful or no specific error message was available.",
        example = "Connection refused to database service at host 'db-host'"
    )
    val errorMessage: String? = null,

    @field:Schema(
        description = "Timestamp indicating when this execution result record was saved to the database.",
        example = "2023-10-27T14:05:20",
        format = "date-time"
    )
    val recordedAt: LocalDateTime,

    @field:Schema(
        description = "The name of the job as it was configured at the time this execution ran (useful if job names can change).",
        example = "Daily Report Generation v1"
    )
    val originalJobName: String? = null
)
