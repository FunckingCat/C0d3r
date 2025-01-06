package ru.davidzh.coder.backend.service

import jakarta.validation.ValidationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.davidzh.coder.backend.controller.UsersController.Companion.log
import ru.davidzh.coder.backend.controller.dto.CreateJobRequest
import ru.davidzh.coder.backend.converter.JobConverter
import ru.davidzh.coder.backend.converter.JobEntityConverter
import ru.davidzh.coder.backend.converter.JobParametersConverter
import ru.davidzh.coder.backend.dao.repository.ExecutionResultRepository
import ru.davidzh.coder.backend.dao.repository.JobRepository
import ru.davidzh.coder.backend.model.ExecutionType.*
import ru.davidzh.coder.backend.model.Job
import ru.davidzh.coder.backend.model.JobStatus
import ru.davidzh.coder.backend.util.extension.getUserAuthentication

@Service
class JobService(
    private val jobParametersConverter: JobParametersConverter,
    private val kubernetesService: KubernetesService,
    private val jobEntityConverter: JobEntityConverter,
    private val jobRepository: JobRepository,
    private val jobConverter: JobConverter,
    private val executionResultRepository: ExecutionResultRepository
) {

    /**
     * Creates new Job
     */
    fun createJob(createJobRequest: CreateJobRequest): Job {

        if (!validateJobName(createJobRequest.name)) throw ValidationException("Job name is invalid")

        val jobParameters = jobParametersConverter.convert(createJobRequest, getUserAuthentication().userId)
        val jobEntity = jobEntityConverter.convert(jobParameters).copy(status = JobStatus.RUNNING) // TODO Доделать на вебхуке, статус должен быть не RUNNING а PENDING

        log.debug("JobService::createJob jobParameters {} jobEntity {}", jobParameters, jobEntity)

        val entity = jobRepository.save(jobEntity)

        when (jobParameters.executionType) {
            ON_DEMAND -> kubernetesService.startJob(jobParameters)
            SCHEDULED -> kubernetesService.startCronJob(jobParameters)
            WEBHOOK -> TODO()
        }

        return jobConverter.convert(jobRepository.findByIdOrNull(entity.id!!)!!,)
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

}