package ru.davidzh.coder.backend.util

import java.util.*

/**
 * Utility object for generating job-related names.
 *
 * This object contains utility functions for constructing names associated with jobs,
 * such as container names. It is particularly useful when a consistent naming convention
 * is needed for tasks like identifying job containers based on user ID, job name, and
 * ordinal values.
 */
object JobNameUtil {

    /**
     * Generates a container name based on the provided user ID, job name, and ordinal.
     *
     * This function constructs a container name by concatenating the `userId`, `jobName`,
     * and `ordinal` values, separated by hyphens.
     *
     * @param userId the ID of the user associated with the job.
     * @param jobName the name of the job.
     * @param ordinal the ordinal value of the job execution.
     * @return a string representing the generated container name in the format:
     *         "userId-jobName-ordinal".
     */
    fun containerName(userId: UUID, jobName: String, ordinal: Int): String = "$userId-$jobName-$ordinal"

}