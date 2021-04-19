package io.averest.observer.repository

import kotlinx.coroutines.Job

object JobRepository {
    val jobs: MutableMap<String, Job> = mutableMapOf()
}
