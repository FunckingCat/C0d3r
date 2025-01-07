package ru.davidzh.coder.backend.service

import jakarta.validation.ValidationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.davidzh.coder.backend.controller.UsersController.Companion.log
import ru.davidzh.coder.backend.controller.dto.CreateJobRequest
import ru.davidzh.coder.backend.converter.JobConverter
import ru.davidzh.coder.backend.converter.JobEntityConverter
import ru.davidzh.coder.backend.converter.JobParametersConverter
import ru.davidzh.coder.backend.dao.repository.JobRepository
import ru.davidzh.coder.backend.model.ExecutionStatus
import ru.davidzh.coder.backend.model.ExecutionType.*
import ru.davidzh.coder.backend.model.Job
import ru.davidzh.coder.backend.model.JobStatus
import ru.davidzh.coder.backend.util.JobNameUtil.containerName
import ru.davidzh.coder.backend.util.extension.getUserAuthentication

@Service
class JobService(
    private val jobParametersConverter: JobParametersConverter,
    private val kubernetesService: KubernetesService,
    private val jobEntityConverter: JobEntityConverter,
    private val jobRepository: JobRepository,
    private val jobConverter: JobConverter
) {

    /**
     * Creates new Job
     */
    fun createJob(createJobRequest: CreateJobRequest): Job {

        if (!validateJobName(createJobRequest.name)) throw ValidationException("Job name is invalid")

        val jobParameters = jobParametersConverter
            .convert(createJobRequest, getUserAuthentication().userId)
            .copy(ordinal = 1)
        val jobEntity = jobEntityConverter.convert(jobParameters)
            .copy(status = if (jobParameters.executionType == WEBHOOK) JobStatus.PENDING else JobStatus.RUNNING)

        log.debug("JobService::createJob jobParameters {} jobEntity {}", jobParameters, jobEntity)

        val entity = jobRepository.save(jobEntity)

        when (jobParameters.executionType) {
            ON_DEMAND -> kubernetesService.startJob(jobParameters)
            SCHEDULED -> kubernetesService.startCronJob(jobParameters)
            WEBHOOK -> Unit
        }

        return jobConverter.convert(jobRepository.findByIdOrNull(entity.id!!)!!)
    }

    fun validateJobName(input: String): Boolean {
        if (input.length > 200) return false
        val regex = Regex("^[a-z0-9]([a-z0-9.-]*[a-z0-9])?$")
        return regex.matches(input)
    }

    /**
     * Returns list of jobs of user
     */
    fun getJobs(): List<Job> = jobRepository.findAllByUserId(getUserAuthentication().userId)
        .map { jobConverter.convert(it) }

    /**
     * Returns job by job id. If user is not owner of the job throws exception.
     */
    fun getJob(id: Long): Job = jobRepository.findById(id)
        .filter { it.userId == getUserAuthentication().userId }
        .map { jobConverter.convert(it) }
        .orElseThrow()

    /**
     * Cancels an active job by its ID.
     *
     * This method terminates the execution of the specified job if it is currently running.
     * If the job is not in a cancellable state, no action is performed.
     *
     * @param id The unique identifier of the job to cancel.
     */
    fun cancelJob(id: Long) {
        val job = jobRepository.findByIdOrNull(id)?: throw ValidationException("Job with id $id not found")

        if (job.userId != getUserAuthentication().userId) throw ValidationException("Job with id $id is not allowed")

        if (job.status != JobStatus.RUNNING) throw ValidationException("Job with id $id is not allowed")

        val jobName = containerName(job.userId, job.name, job.ordinal!!)

        if (job.executionType == SCHEDULED) {
            kubernetesService.terminateCronJob(jobName)
        } else {
            kubernetesService.terminateJob(jobName)
        }

        jobRepository.save(
            job.copy(
                status = JobStatus.CANCELLED,
                executionResults = job.executionResults
                    ?.map {
                        if (it.status == ExecutionStatus.RUNNING) it.copy(status = ExecutionStatus.CANCELLED) else it
                    }))
    }

    /**
     * Deletes a job by its ID.
     *
     * This method removes the specified job and its associated metadata from the system.
     * Use with caution, as this action is irreversible.
     *
     * @param id The unique identifier of the job to delete.
     */
    fun deleteJob(id: Long) {

        val job = jobRepository.findByIdOrNull(id)?: throw ValidationException("Job with id $id not found")

        if (job.userId != getUserAuthentication().userId) throw ValidationException("Job with id $id is not allowed")

        try {
            cancelJob(id)
        } catch (_: Exception) { }

        jobRepository.save(job.copy(deleted = true))
    }

    /**
     * Reruns a job by its ID.
     *
     * This method creates a new execution instance of the specified job using the same
     * configuration as the original job. It is typically used for retrying failed jobs or
     * rerunning completed jobs.
     *
     * @param id The unique identifier of the job to rerun.
     */
    fun rerunJob(id: Long) {
        val job = (jobRepository.findByIdOrNull(id) ?: throw IllegalStateException("Job with ID $id not found"))
            .apply { ordinal = ordinal?.plus(1) }

        if (job.userId != getUserAuthentication().userId) throw IllegalStateException("Access denied")

        if (job.status == JobStatus.RUNNING) throw IllegalStateException("Job with ID $id is already running")

        if (job.deleted == true) throw ValidationException("Job with id $id is deleted")

        val parameters = jobParametersConverter.convert(job)

        if (parameters.executionType == SCHEDULED) {
            kubernetesService.startCronJob(parameters)
        } else {
            kubernetesService.startJob(parameters)
        }

        jobRepository.save(job.copy(status = JobStatus.RUNNING))
    }

    fun startWebHookJob(jobId: Long) {
        val job = (jobRepository.findByIdOrNull(jobId) ?: throw IllegalStateException("Job with ID $jobId not found"))
            .apply { ordinal = ordinal?.plus(1) }

        if (job.userId != getUserAuthentication().userId) throw IllegalStateException("Access denied")

        if (job.status != JobStatus.PENDING) throw IllegalStateException("Job with ID $jobId is not pending")

        val parameters = jobParametersConverter.convert(job)

        kubernetesService.startJob(parameters)

        jobRepository.save(job.copy(status = JobStatus.RUNNING))
    }

}