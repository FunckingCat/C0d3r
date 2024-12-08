package ru.davidzh.coder.backend.service

import org.springframework.stereotype.Service
import ru.davidzh.coder.backend.model.Job

@Service
class JobService {

    fun createJob(job: Job): Job = job

}