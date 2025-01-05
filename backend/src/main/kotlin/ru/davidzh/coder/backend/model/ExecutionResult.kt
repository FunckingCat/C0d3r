package ru.davidzh.coder.backend.model

import java.time.LocalDateTime

data class ExecutionResult(
    /** Уникальный идентификатор выполнения */
    val id: Long? = null,
    /** Уникальный идентификатор выполнения */
    val jobId: Long,
    /** Время начала выполнения задачи */
    val startedAt: LocalDateTime,
    /** Время завершения выполнения задачи */
    val finishedAt: LocalDateTime? = null,
    /** Статус выполнения задачи */
    val status: ExecutionStatus,
    /** Логи выполнения задачи */
    val logs: List<String>? = null,
    /** Код завершения процесса (например, 0 для успешного завершения) */
    val exitCode: Int?,
    /** Ошибки, возникшие во время выполнения (если есть) */
    val errorMessage: String? = null,
    /** Дата и время записи результата в базу данных */
    val recordedAt: LocalDateTime
)
