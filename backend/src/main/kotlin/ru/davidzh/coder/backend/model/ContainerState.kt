package ru.davidzh.coder.backend.model

import java.time.LocalDateTime

data class ContainerState(
    /** Unique identifier for the container */
    val containerName: String,
    /** The current status of the job (Pending, Running, Completed, Failed, etc.) */
    val status: ExecutionStatus,
    /** Check time */
    val checkTime: LocalDateTime,
    /** Logs for the job execution */
    val logs: List<String>
)
