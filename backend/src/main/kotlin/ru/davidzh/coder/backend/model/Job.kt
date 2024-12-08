package ru.davidzh.coder.backend.model

import java.time.LocalDateTime

data class Job(
    /** Уникальный идентификатор задачи */
    val id: String,
    /** Идентификатор пользователя, создавшего задачу */
    val userId: String,
    /** Название задачи */
    val name: String,
    /** Ссылка на Docker-образ или локальный .tar */
    val dockerImage: String,
    /** Переменные окружения */
    val environmentVariables: Map<String, String> = emptyMap(),
    /** Тип запуска задачи */
    val executionType: ExecutionType,
    /** Планировщик CronJob (если используется) */
    val schedule: String? = null,
    /** Текущий статус задачи */
    val status: TaskStatus = TaskStatus.PENDING,
    /** Время создания задачи */
    val createdAt: LocalDateTime = LocalDateTime.now(),
    /** Время начала задачи */
    val startedAt: LocalDateTime? = null,
    /** Время завершения задачи */
    val completedAt: LocalDateTime? = null,
    /** Результат выполнения задачи */
    val result: String? = null,
    /** Логи задачи */
    val logs: List<String> = emptyList()
)
