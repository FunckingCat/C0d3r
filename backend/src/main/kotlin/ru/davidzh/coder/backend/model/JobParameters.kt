package ru.davidzh.coder.backend.model

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "Represents the configurable parameters required to define a job's execution details, including ownership, container settings, and execution strategy.")
data class JobParameters(

    @field:Schema(
        description = "Unique identifier (UUID) of the user creating or owning the job.",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "123e4567-e89b-12d3-a456-426614174000"
    )
    val userId: UUID,

    @field:Schema(
        description = "Optional unique identifier (UUID) of the group this job belongs to or is associated with.",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        example = "a1b2c3d4-e5f6-7890-1234-567890abcdef"
    )
    val groupId: UUID? = null,

    @field:Schema(
        description = "User-defined name for the job, used for identification.",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "Data Aggregation Task"
    )
    val name: String,

    @field:Schema(
        description = "Reference to the Docker image for job execution (e.g., 'image:tag', 'registry/repo/image:tag').",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "my-data-processor:1.2.0"
    )
    val dockerImage: String,

    @field:ArraySchema(
        schema = Schema(description = "Command or argument string.", example = "run.sh"),
        minItems = 1,
    )
    @field:Schema(
        description = "The command and its arguments to execute within the container.",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    val command: List<String>,

    @field:Schema(
        description = "Map of environment variables (key-value pairs) to set within the container.",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        example = """{"INPUT_PATH": "/data", "OUTPUT_BUCKET": "s3://my-results-bucket"}"""
    )
    val environmentVariables: Map<String, String> = emptyMap(),

    @field:Schema(
        description = "Specifies how the job execution is triggered (e.g., IMMEDIATE, SCHEDULED, WEBHOOK).",
        requiredMode = Schema.RequiredMode.REQUIRED,
        implementation = ExecutionType::class

    )
    val executionType: ExecutionType,

    @field:Schema(
        description = "Cron-formatted schedule string. Required only if `executionType` is 'SCHEDULED'.",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        example = "0 4 * * *"
    )
    val schedule: String? = null,

    @field:Schema(
        description = "Optional ordinal number, potentially for ordering jobs in a sequence or workflow.",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        example = "1"
    )
    val ordinal: Int? = null,
)
