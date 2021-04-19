package io.averest.observer.infrastructure

interface Designator {
    /**
     * to identifier the single job
     */
    val identifier: String

    /**
     * to execute the Job
     */
    fun launchJob()
}
