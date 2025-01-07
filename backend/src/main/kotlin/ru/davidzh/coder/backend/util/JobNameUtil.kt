package ru.davidzh.coder.backend.util

import java.util.*

object JobNameUtil {

    fun containerName(userId: UUID, jobName: String, ordinal: Int): String = "$userId-$jobName-$ordinal"

}