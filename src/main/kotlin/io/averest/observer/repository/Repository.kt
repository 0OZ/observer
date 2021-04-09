package io.averest.observer.repository

import kotlinx.coroutines.Job

class Repository {
    companion object {
        val jobs: MutableMap<String, Job> = mutableMapOf()
    }
}
