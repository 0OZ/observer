package io.averest.observer.domain

import io.averest.observer.aggregator.SubscribeJob
import io.averest.observer.infrastructure.Designator
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

class Subscriber(private val callable: Designator) {
    private val poolSize = 64
    private val executor = Executors.newFixedThreadPool(poolSize)
    private val type: String = callable.javaClass.simpleName
    private val ident: String = callable.identifier

    fun addJob() {
        val observableJob = SubscribeJob(type, ident)
        call(observableJob)
    }

    private fun call(observableJob: SubscribeJob) {
        if (observableJob.getJob() == null || observableJob.getJob()!!.isCompleted) {
            val job = GlobalScope.launch(executor.asCoroutineDispatcher()) {
                callable.run()
            }
            observableJob.setJob(job)
            printer("${type}|${ident}|subscribe ✓")
        }
    }

    private fun printer(message: Any) {
        val ts = SimpleDateFormat("HH:mm:ss").format(Date())
        val msg = String.format(
            "%-25s%5s",
            "[${this::class.simpleName}|$ts]: ",
            message.toString()
        )
        println(msg)
    }
}
