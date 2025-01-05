package ru.davidzh.coder.backend.dao.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import ru.davidzh.coder.backend.model.ExecutionType
import java.time.LocalDateTime
import java.util.*

@Table("jobs")
data class JobEntity(
    /** Идентификатор задачи */
    @Id val id: Long? = null,
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
    val environmentVariables: String,
    /** Тип запуска задачи */
    val executionType: ExecutionType,
    /** Планировщик CronJob (если используется) */
    val schedule: String? = null
) {
}