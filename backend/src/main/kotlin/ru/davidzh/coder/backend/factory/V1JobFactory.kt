package ru.davidzh.coder.backend.factory

import io.kubernetes.client.openapi.models.*
import ru.davidzh.coder.backend.model.Job

object V1JobFactory {

    fun createV1Job(job: Job): V1Job {
        return V1Job()
            .apiVersion("batch/v1")
            .kind("Job")
            .metadata(
                V1ObjectMeta()
                    .name(job.name)
            )
            .spec(
                V1JobSpec()
                    .template(
                        V1PodTemplateSpec()
                            .spec(
                                V1PodSpec()
                                    .containers(listOf(containerFromJob(job)))
                                    .restartPolicy("Never")
                            )
                    )
                    .backoffLimit(4)
            )
    }

    private fun containerFromJob(job: Job): V1Container = V1Container()
        .name(job.name)
        .image(job.dockerImage)
        .command(job.command)

}