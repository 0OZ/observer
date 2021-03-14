package io.averest.observer.aggregator

import io.averest.observer.domain.Observable
import kotlinx.coroutines.Job

class SubscribeJob(type: String, ident: String) {
    private val observable = Observable(type, ident)
    fun getJob() = SubscribeMap.providerJobs[observable.id]
    fun setJob(job: Job) {
        SubscribeMap.providerJobs[observable.id] = job
    }
}

