package io.averest.observer.domain.application

import io.averest.observer.domain.factory.Issuer
import io.averest.observer.infrastructure.Designator
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import org.apache.logging.log4j.LogManager
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Subscriber(
    private val callable: Designator,
    private val poolSize: Int = 64,
    private val executor: ExecutorService = Executors.newFixedThreadPool(poolSize),
    private val identifier: Issuer = Issuer(callable.javaClass.simpleName, callable.identifier),
) {
    private val logger = LogManager.getFormatterLogger(this.javaClass)
    fun call() {
        if (identifier.getJob() == null || identifier.getJob()!!.isCompleted) {
            val job = GlobalScope.launch(executor.asCoroutineDispatcher()) {
                callable.run()
            }
            identifier.setJob(job)
            logger.trace("${identifier.type}|${identifier.ident}|subscribe ✓")
        }
    }
}
