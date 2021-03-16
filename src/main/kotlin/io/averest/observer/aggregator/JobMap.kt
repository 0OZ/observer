package io.averest.observer.aggregator

import kotlinx.coroutines.Job

class JobMap {
    companion object {
        val providerJobs: MutableMap<String, Job> = mutableMapOf()
    }
}
