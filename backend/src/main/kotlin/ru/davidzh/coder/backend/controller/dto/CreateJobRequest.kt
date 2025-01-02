package ru.davidzh.coder.backend.controller.dto

import ru.davidzh.coder.backend.model.ExecutionType

data class CreateJobRequest(
    /** Название задачи */
    val name: String,
    /** Ссылка на Docker-образ или локальный .tar */
    val dockerImage: String,
    /** Команда с которой должна запуститься задача */
    val command: List<String>,
    /** Переменные окружения */
    val environmentVariables: Map<String, String> = emptyMap(),
    /** Тип запуска задачи */
    val executionType: ExecutionType,
    /** Планировщик CronJob (если используется) */
    val schedule: String? = null,
)
