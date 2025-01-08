package ru.davidzh.coder.backend.factory

import io.kubernetes.client.openapi.models.*
import ru.davidzh.coder.backend.model.JobParameters
import ru.davidzh.coder.backend.util.JobNameUtil.containerName

/**
 * Factory object for creating Kubernetes Job and CronJob objects based on job parameters.
 *
 * This object contains methods to construct Kubernetes `Job` and `CronJob` resources by transforming
 * the provided [JobParameters] into corresponding Kubernetes API representations.
 */
object V1JobFactory {

    /**
     * Creates a Kubernetes [V1Job] based on the provided job parameters.
     *
     * @param jobParameters the job parameters to configure the Kubernetes Job.
     * @return a [V1Job] object with the appropriate configuration.
     */
    fun createV1Job(jobParameters: JobParameters): V1Job {
        return V1Job()
            .apiVersion("batch/v1")
            .kind("Job")
            .metadata(metadataFromParameters(jobParameters))
            .spec(v1JobSpecFromJobParameters(jobParameters))
    }

    /**
     * Creates a Kubernetes [V1CronJob] based on the provided job parameters.
     *
     * @param jobParameters the job parameters to configure the Kubernetes CronJob.
     * @return a [V1CronJob] object with the appropriate configuration.
     */
    fun createV1CronJob(jobParameters: JobParameters): V1CronJob {
        return V1CronJob()
            .apiVersion("batch/v1")
            .kind("CronJob")
            .metadata(metadataFromParameters(jobParameters))
            .spec(
                V1CronJobSpec()
                    .schedule(jobParameters.schedule)
                    .jobTemplate(
                        V1JobTemplateSpec()
                            .spec(v1JobSpecFromJobParameters(jobParameters))
                    )
            )
    }

    private fun v1JobSpecFromJobParameters(jobParameters: JobParameters): V1JobSpec {
        return V1JobSpec()
            .template(
                V1PodTemplateSpec()
                    .spec(
                        V1PodSpec()
                            .containers(listOf(containerFromJob(jobParameters)))
                            .restartPolicy("Never")
                    )
            )
            .backoffLimit(4)
    }

    private fun metadataFromParameters(jobParameters: JobParameters) = V1ObjectMeta()
        .name(containerName(jobParameters.userId, jobParameters.name, jobParameters.ordinal!!))

    private fun containerFromJob(jobParameters: JobParameters): V1Container = V1Container()
        .name(containerName(jobParameters.userId, jobParameters.name, jobParameters.ordinal!!))
        .image(jobParameters.dockerImage)
        .command(jobParameters.command)

}