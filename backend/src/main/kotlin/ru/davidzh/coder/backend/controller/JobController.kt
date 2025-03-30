package ru.davidzh.coder.backend.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.davidzh.coder.backend.aop.annotation.LogExecution
import ru.davidzh.coder.backend.controller.dto.CreateJobRequest
import ru.davidzh.coder.backend.controller.model.RestError
import ru.davidzh.coder.backend.model.Job
import ru.davidzh.coder.backend.service.JobService
import ru.davidzh.coder.backend.util.extension.asResponseEntity

/**
 * REST controller for managing jobs in the system.
 *
 * Provides endpoints for creating, retrieving, updating, and managing the lifecycle of jobs.
 *
 * @param jobService the service responsible for handling job-related logic.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/job")
@Tag(name = "Job Management", description = "Endpoints for creating, retrieving, updating, and managing the lifecycle of jobs.")
class JobController(
    private val jobService: JobService
) {

    /**
     * Creates a new job.
     *
     * @param createJobRequest the request containing job details for creation.
     * @return a [ResponseEntity] with the details of the created job.
     */
    @LogExecution
    @PostMapping
    @Operation(
        summary = "Create Job",
        description = "Creates a new job based on the provided details."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "201", description = "Job created successfully", // Use 201 for successful creation
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = Job::class))]
        ),
        ApiResponse(
            responseCode = "500", description = "Internal server error",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = RestError::class))]
        )
    ])
    fun createJob(@RequestBody createJobRequest: CreateJobRequest): ResponseEntity<Job> =
        jobService.createJob(createJobRequest).asResponseEntity()

    /**
     * Retrieves a list of all jobs.
     *
     * @return a [ResponseEntity] containing a list of jobs.
     */
    @LogExecution
    @GetMapping
    @Operation(
        summary = "Get All Jobs",
        description = "Retrieves a list of all jobs available in the system."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200", description = "Successfully retrieved list of jobs",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = ArraySchema(schema = Schema(implementation = Job::class)))] // Use ArraySchema for lists
        ),
        ApiResponse(
            responseCode = "500", description = "Internal server error",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = RestError::class))]
        )
    ])
    fun getJobs(): ResponseEntity<List<Job>> = jobService.getJobs().asResponseEntity()

    /**
     * Retrieves the details of a specific job by its ID.
     *
     * @param id the ID of the job to retrieve.
     * @return a [ResponseEntity] containing the details of the requested job.
     */
    @LogExecution
    @GetMapping("/{id}")
    @Operation(
        summary = "Get Job by ID",
        description = "Retrieves the details of a specific job using its unique ID."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200", description = "Successfully retrieved job details",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = Job::class))]
        ),
        ApiResponse(
            responseCode = "500", description = "Internal server error",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = RestError::class))]
        )
    ])
    fun getJob(@PathVariable id: Long): ResponseEntity<Job> = jobService.getJob(id).asResponseEntity()

    /**
     * Cancels a specific job by its ID.
     *
     * @param id the ID of the job to cancel.
     * @return a [ResponseEntity] indicating the result of the cancel operation.
     */
    @LogExecution
    @PostMapping("/cancel/{id}")
    @Operation(
        summary = "Cancel Job",
        description = "Requests cancellation of a specific job by its ID. The job might not be cancelled immediately depending on its state."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200", description = "Job cancellation request accepted", // Or 204 if truly no content ever
            content = [Content()]
        ),
        ApiResponse(
            responseCode = "500", description = "Internal server error",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = RestError::class))]
        )
    ])
    fun cancelJob(@PathVariable id: Long): ResponseEntity<Unit> = jobService.cancelJob(id).asResponseEntity()

    /**
     * Reruns a specific job by its ID.
     *
     * @param id the ID of the job to rerun.
     * @return a [ResponseEntity] indicating the result of the rerun operation.
     */
    @LogExecution
    @PostMapping("/rerun/{id}")
    @Operation(
        summary = "Rerun Job",
        description = "Requests a rerun of a specific job (e.g., a failed or completed job) by its ID."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200", description = "Job rerun request accepted",
            content = [Content()]
        ),
        ApiResponse(
            responseCode = "500", description = "Internal server error",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = RestError::class))]
        )
    ])
    fun rerunJob(@PathVariable id: Long): ResponseEntity<Unit> = jobService.rerunJob(id).asResponseEntity()

    /**
     * Deletes a specific job by its ID.
     *
     * @param id the ID of the job to delete.
     * @return a [ResponseEntity] indicating the result of the delete operation.
     */
    @LogExecution
    @PostMapping("/delete/{id}")
    @Operation(
        summary = "Delete Job",
        description = "Permanently deletes a specific job by its ID."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200", description = "Job deleted successfully", // Or 204 No Content
            content = [Content()]
        ),
        ApiResponse(
            responseCode = "500", description = "Internal server error",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = RestError::class))]
        )
    ])
    fun deleteJob(@PathVariable id: Long): ResponseEntity<Unit> = jobService.deleteJob(id).asResponseEntity()

    /**
     * Starts a webhook job for a specific job ID.
     *
     * @param id the ID of the job to start as a webhook.
     * @return a [ResponseEntity] indicating the result of the webhook job start operation.
     */
    @LogExecution
    @PostMapping("/webhook/run/{id}")
    @Operation(
        summary = "Start Webhook Job",
        description = "Triggers the webhook associated with a specific job ID."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200", description = "Webhook job trigger request accepted",
            content = [Content()]
        ),
        ApiResponse(
            responseCode = "500", description = "Internal server error",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = RestError::class))]
        )
    ])
    fun startWebhook(@PathVariable id: Long): ResponseEntity<Unit> = jobService.startWebHookJob(id).asResponseEntity()

}