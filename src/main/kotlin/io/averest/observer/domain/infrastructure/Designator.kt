package io.averest.observer.domain.infrastructure

interface Designator {
    /**
     * to identifier the single job
     */
    val identifier: String

    /**
     * to execute the Job
     */
    fun run()
}
