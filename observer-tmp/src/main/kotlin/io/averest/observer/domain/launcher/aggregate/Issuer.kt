package io.averest.observer.domain.launcher.aggregate

import io.averest.observer.domain.launcher.model.Tag
import io.averest.observer.repository.JobRepository
import kotlinx.coroutines.Job

class Issuer(val type: String, val ident: String) {
    private val tag = Tag(type, ident)

    fun getJob() = JobRepository.jobs[tag.id]
    fun setJob(job: Job) {
        JobRepository.jobs[tag.id] = job
    }
}

