package io.averest.observer.domain.factory

import io.averest.observer.domain.model.Tag
import io.averest.observer.repository.Repository
import kotlinx.coroutines.Job

class Issuer(val type: String, val ident: String) {
    private val tag = Tag(type, ident)

    fun getJob() = Repository.jobs[tag.id]
    fun setJob(job: Job) {
        Repository.jobs[tag.id] = job
    }
}

