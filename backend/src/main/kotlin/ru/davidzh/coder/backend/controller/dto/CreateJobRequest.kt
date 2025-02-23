package ru.davidzh.coder.backend.controller.dto

import io.swagger.v3.oas.annotations.media.Schema
import ru.davidzh.coder.backend.model.ExecutionType

/**
 * Data class representing a request to create a new job.
 *
 * This class encapsulates the necessary information for defining a job
 * that can be scheduled and executed in a containerized environment.
 * It includes job parameters like the Docker image, command, environment variables,
 * and execution type. Optionally, a cron schedule can be defined.
 */
@Schema(description = "Request to create a new job, including details such as the job name, Docker image, command, environment variables, and execution type.")
data class CreateJobRequest(

    @Schema(description = "Название задачи")
    val name: String,

    @Schema(description = "Ссылка на Docker-образ или локальный .tar")
    val dockerImage: String,

    @Schema(description = "Команда с которой должна запуститься задача")
    val command: List<String>,

    @Schema(description = "Переменные окружения")
    val environmentVariables: Map<String, String> = emptyMap(),

    @Schema(description = "Тип запуска задачи")
    val executionType: ExecutionType,

    @Schema(description = "Планировщик CronJob (если используется)")
    val schedule: String? = null
)

