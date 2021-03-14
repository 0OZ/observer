package io.averest.observer.infrastructure

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job


interface Runner {
    val runEventLoop: Boolean

    /**
     * Launch the Application
     */
    fun start()


    /**
     *  add events to event loop
     */
    fun CoroutineScope.addEvent(): Job
}
