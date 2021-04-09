package io.averest.observer.domain.service

import io.averest.observer.domain.application.Subscriber
import io.averest.observer.infrastructure.Accelerator
import io.averest.observer.infrastructure.Designator
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

abstract class Launcher : Accelerator {
    override val runEventLoop = true
    private val executor: ExecutorService = Executors.newFixedThreadPool(64)
    private val sleepTimer = 120_000L

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
                eventSuspender()
                eventLoopIsRunning = runEventLoop
            }
        }
    }

    private fun CoroutineScope.eventSuspender() = launch(executor.asCoroutineDispatcher()) {
        while (eventLoopIsRunning) {
            addEvent()
            delay(sleepTimer)
        }
    }

    fun <T> CoroutineScope.launchJobs(eventList: List<T>, call: (callableEvent: T) -> Designator?) =
        launch(executor.asCoroutineDispatcher()) {
            eventList.forEach { event ->
                val eventClass = async { awaitCall(call, event) }
                eventClass.await()?.let { Subscriber(it).call() }
            }
        }


    private fun <T> awaitCall(
        buildEvent: (event: T) -> Designator?,
        event: T,
    ): Designator? = buildEvent(event)


    fun printer(message: Any) {
        val ts = SimpleDateFormat("HH:mm:ss").format(Date())
        val msg = String.format(
            "%-25s%5s",
            "[${this::class.simpleName}|${Thread.currentThread()}|$ts]: ",
            message.toString()
        )
        println(msg)
    }
}
