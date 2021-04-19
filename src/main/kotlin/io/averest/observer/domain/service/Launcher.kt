package io.averest.observer.domain.service

import io.averest.observer.domain.application.Publisher
import io.averest.observer.infrastructure.Accelerator
import io.averest.observer.infrastructure.Designator
import kotlinx.coroutines.*
import org.apache.logging.log4j.LogManager
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

abstract class Launcher(
    override val runEventLoop: Boolean = true,
    private val executor: ExecutorService = Executors.newFixedThreadPool(64),
    private val sleepTimer: Long = 1_000L
) : Accelerator {
    val logger = LogManager.getFormatterLogger(this.javaClass)!!

    companion object {
        var eventLoopIsRunning = false
        fun stop() {
            eventLoopIsRunning = false
        }
    }

    override fun start() {
        addEventLoops {}
    }


    fun addEventLoops(eventLoop: CoroutineScope.() -> Unit) {
        if (!eventLoopIsRunning) {
            GlobalScope.launch {
                eventLoop()
                carrier()
                eventLoopIsRunning = runEventLoop
            }
        }
    }

    private fun CoroutineScope.carrier() = launch(executor.asCoroutineDispatcher()) {
        while (eventLoopIsRunning) {
            logger.info("looping")
            addEvent()
            delay(sleepTimer)
        }
    }

    fun <T> CoroutineScope.launchJobs(identList: List<T>, callable: (jobIdentifier: T) -> Designator) {
        identList.forEach {
            launch {
                Publisher(callable(it)).commit()
            }
        }
    }
}
