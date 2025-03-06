package ru.davidzh.coder.backend.model

import java.util.*

data class JobParameters(
    /** Идентификатор пользователя, создавшего задачу */
    val userId: UUID,
    /** Идентификатор группы, которой принадлежит задача */
    val groupId: UUID? = null,
    /** Название задачи */
    val name: String,
    /** Ссылка на Docker-образ или локальный .tar */
    val dockerImage: String,
    /** Команда с которой должен быть запущен контейнер */
    val command: List<String>,
    /** Переменные окружения */
    val environmentVariables: Map<String, String> = emptyMap(),
    /** Тип запуска задачи */
    val executionType: ExecutionType,
    /** Планировщик CronJob (если используется) */
    val schedule: String? = null,
    /** Порядковый номер запуска */
    val ordinal: Int? = null,
)
