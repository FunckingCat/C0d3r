package ru.davidzh.coder.backend.controller.dto

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import ru.davidzh.coder.backend.model.ExecutionType
import java.util.*

/**
 * Data class representing a request to create a new job.
 *
 * This class encapsulates the necessary information for defining a job
 * that can be scheduled and executed in a containerized environment.
 * It includes job parameters like the Docker image, command, environment variables,
 * and execution type. Optionally, a cron schedule can be defined.
 */
@Schema(description = "Request payload containing all necessary details to define and create a new job for containerized execution.")
data class CreateJobRequest(

    @field:Schema(
        description = "A user-defined name for the job, used for identification.",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "Daily Report Generation"
    )
    var name: String,

    @field:Schema(
        description = "Optional unique identifier (UUID) of the group this job belongs to or is associated with.",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        example = "a1b2c3d4-e5f6-7890-1234-567890abcdef"
    )
    val groupId: UUID? = null,

    @field:Schema(
        description = "The reference to the Docker image to be used for executing the job. Can be a full registry path (e.g., 'ubuntu:latest', 'myregistry.com/myimage:v1.2') or potentially a reference to a local .tar archive (implementation-dependent).",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "python:3.9-slim"
    )
    val dockerImage: String,


    @field:ArraySchema(
        schema = Schema(
            description = "The command and its arguments to be executed within the Docker container. Corresponds to Docker's CMD or ENTRYPOINT.",
            example = "/app/run_report.sh"
        ),
        minItems = 1
    )
    @field:Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    val command: List<String>,

    @field:Schema(
        description = "A map of environment variables (key-value pairs) to be set within the container during job execution.",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        example = """{"API_KEY": "secret-key-value", "DATABASE_URL": "postgres://user:pass@host:port/db"}"""
    )
    val environmentVariables: Map<String, String> = emptyMap(),

    @field:Schema(
        description = "Specifies how the job should be executed (e.g., immediately upon creation, according to a schedule, triggered by a webhook).",
        requiredMode = Schema.RequiredMode.REQUIRED,
        implementation = ExecutionType::class
    )
    val executionType: ExecutionType,

    @field:Schema(
        description = "A cron-formatted schedule string that defines when the job should run. Required only if `executionType` is 'SCHEDULED'. See cron syntax documentation for details.",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        example = "0 2 * * MON-FRI"
    )
    val schedule: String? = null
)

