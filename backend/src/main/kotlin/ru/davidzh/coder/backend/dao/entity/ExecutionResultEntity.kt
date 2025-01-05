package ru.davidzh.coder.backend.dao.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import ru.davidzh.coder.backend.model.ExecutionStatus
import java.time.LocalDateTime

@Table("execution_results")
data class ExecutionResultEntity(
    /** Идентификатор результата выполнения */
    @Id val id: Long? = null,
    /** Идентификатор задачи к которой относится результат */
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
    val exitCode: Int? = 0,
    /** Ошибки, возникшие во время выполнения (если есть) */
    val errorMessage: String? = null,
    /** Дата и время записи результата в базу данных */
    val recordedAt: LocalDateTime
) {
}