package ru.davidzh.coder.backend.model

import java.time.LocalDateTime
import java.util.*

data class Job(
    /** Идентификатор задачи */
    val id: Long? = null,
    /** Идентификатор пользователя которому принадлежит задача */
    val userId: UUID,
    /** Название задачи */
    val name: String,
    /** Время создания задачи */
    val createdAt: LocalDateTime?,
    /** Ссылка на Docker-образ или локальный .tar */
    val dockerImage: String,
    /** Команда с которой должен быть запущен контейнер */
    val command: List<String>,
    /** Переменные окружения */
    val environmentVariables: Map<String, String>,
    /** Тип запуска задачи */
    val executionType: ExecutionType,
    /** Планировщик CronJob (если используется) */
    val schedule: String? = null,
    /** Результаты выполнения */
    val executionResults: List<ExecutionResult>?
) {
}