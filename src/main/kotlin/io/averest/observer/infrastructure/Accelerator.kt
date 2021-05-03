package io.averest.observer.infrastructure

import kotlinx.coroutines.Job


interface Accelerator {
    val runEventLoop: Boolean
    /**
     * Launch the Application
     */
    fun start()
    fun mainLoop(): Thread
    fun optionalCall(): Thread
    fun commitJobs()
}
