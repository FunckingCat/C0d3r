package ru.davidzh.coder.backend.model

data class IntermediateJobState(
    /** Unique identifier for the job from Kubernetes */
    val originalJobName: String,
    /** Unique identifier for the job */
    val jobName: String,
    /** The current status of the job (Pending, Running, Completed, Failed, etc.) */
    val status: ExecutionStatus,
    /** List of container states of the job */
    val containerStates: List<ContainerState> = emptyList()
)
