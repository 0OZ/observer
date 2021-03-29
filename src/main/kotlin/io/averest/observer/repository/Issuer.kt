package io.averest.observer.repository

import kotlinx.coroutines.Job

class Issuer {
    companion object {
        val jobs: MutableMap<String, Job> = mutableMapOf()
    }
}
