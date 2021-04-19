package io.averest.observer.domain.application

import io.averest.observer.domain.aggregate.Issuer
import io.averest.observer.infrastructure.Designator
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.LogManager

class Publisher(
    private val callable: Designator,
    private val identifier: Issuer = Issuer(callable.javaClass.simpleName, callable.identifier),
) {
    private val logger = LogManager.getFormatterLogger(this.javaClass)
    fun commit() = runBlocking {
        if (identifier.getJob() == null || identifier.getJob()!!.isCompleted) {
            try {
                val job = launch { callable.launchJob() }
                identifier.setJob(job)
                logger.info("${identifier.type}${identifier.ident} subscribe ✓")
            } catch (e: Exception) {
                logger.error(e)
            }
        }
    }
}
