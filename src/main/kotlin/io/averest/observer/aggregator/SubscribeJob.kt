package io.averest.observer.aggregator

import io.averest.observer.domain.Identifier
import kotlinx.coroutines.Job

class SubscribeJob(type: String, ident: String) {
    private val observable = Identifier(type, ident)
    fun getJob() = JobMap.providerJobs[observable.id]
    fun setJob(job: Job) {
        JobMap.providerJobs[observable.id] = job
    }
}

