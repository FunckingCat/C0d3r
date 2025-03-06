package ru.davidzh.coder.backend.controller

import org.springframework.web.bind.annotation.*
import ru.davidzh.coder.backend.aop.annotation.LogExecution
import ru.davidzh.coder.backend.controller.dto.CreateJobRequest
import ru.davidzh.coder.backend.service.JobService
import ru.davidzh.coder.backend.util.extension.asResponseEntity
import java.util.*

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
    fun createJob(@RequestBody createJobRequest: CreateJobRequest) =
        jobService.createJob(createJobRequest).asResponseEntity()

    /**
     * Retrieves a list of all jobs.
     *
     * @return a [ResponseEntity] containing a list of jobs.
     */
    @LogExecution
    @GetMapping
    fun getJobs() = jobService.getJobs().asResponseEntity()

    /**
     * Retrieves the details of a specific job by its ID.
     *
     * @param id the ID of the job to retrieve.
     * @return a [ResponseEntity] containing the details of the requested job.
     */
    @LogExecution
    @GetMapping("/{id}")
    fun getJob(@PathVariable id: Long) = jobService.getJob(id).asResponseEntity()

    /**
     * Cancels a specific job by its ID.
     *
     * @param id the ID of the job to cancel.
     * @return a [ResponseEntity] indicating the result of the cancel operation.
     */
    @LogExecution
    @PostMapping("/cancel/{id}")
    fun cancelJob(@PathVariable id: Long) = jobService.cancelJob(id).asResponseEntity()

    /**
     * Reruns a specific job by its ID.
     *
     * @param id the ID of the job to rerun.
     * @return a [ResponseEntity] indicating the result of the rerun operation.
     */
    @LogExecution
    @PostMapping("/rerun/{id}")
    fun rerunJob(@PathVariable id: Long) = jobService.rerunJob(id).asResponseEntity()

    /**
     * Deletes a specific job by its ID.
     *
     * @param id the ID of the job to delete.
     * @return a [ResponseEntity] indicating the result of the delete operation.
     */
    @LogExecution
    @PostMapping("/delete/{id}")
    fun deleteJob(@PathVariable id: Long) = jobService.deleteJob(id).asResponseEntity()

    /**
     * Starts a webhook job for a specific job ID.
     *
     * @param id the ID of the job to start as a webhook.
     * @return a [ResponseEntity] indicating the result of the webhook job start operation.
     */
    @LogExecution
    @PostMapping("/webhook/run/{id}")
    fun startWebhook(@PathVariable id: Long) = jobService.startWebHookJob(id).asResponseEntity()

}