package io.averest.observer.aggregator

import kotlinx.coroutines.Job

class SubscribeMap {
    companion object {
        val providerJobs: MutableMap<String, Job> = mutableMapOf()
    }
}
