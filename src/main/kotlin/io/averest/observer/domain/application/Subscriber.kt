package io.averest.observer.domain.application

import io.averest.observer.domain.factory.Issuer
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
    private val identifier = Issuer(callable.javaClass.simpleName, callable.identifier)

    fun call() {
        if (identifier.getJob() == null || identifier.getJob()!!.isCompleted) {
            val job = GlobalScope.launch(executor.asCoroutineDispatcher()) {
                callable.run()
            }
            identifier.setJob(job)
            printer("${identifier.type}|${identifier.ident}|subscribe ✓")
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
