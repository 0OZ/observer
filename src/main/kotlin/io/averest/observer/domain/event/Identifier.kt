package io.averest.observer.domain.event

import io.averest.observer.domain.model.Tag
import io.averest.observer.repository.Issuer
import kotlinx.coroutines.Job

class Identifier(val type: String, val ident: String) {
    private val tag = Tag(type, ident)

    fun getJob() = Issuer.jobs[tag.id]
    fun setJob(job: Job) {
        Issuer.jobs[tag.id] = job
    }
}

