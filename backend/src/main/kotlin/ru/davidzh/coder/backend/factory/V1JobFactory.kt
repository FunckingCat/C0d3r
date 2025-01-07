package ru.davidzh.coder.backend.factory

import io.kubernetes.client.openapi.models.*
import ru.davidzh.coder.backend.model.JobParameters
import ru.davidzh.coder.backend.util.JobNameUtil.containerName

object V1JobFactory {

    fun createV1Job(jobParameters: JobParameters): V1Job {
        return V1Job()
            .apiVersion("batch/v1")
            .kind("Job")
            .metadata(metadataFromParameters(jobParameters))
            .spec(v1JobSpecFromJobParameters(jobParameters))
    }

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