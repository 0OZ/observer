package io.averest.observer.domain.launcher.application

import io.averest.observer.domain.launcher.aggregate.Issuer
import io.averest.observer.infrastructure.Designator
import kotlinx.coroutines.*
import org.apache.logging.log4j.LogManager
import kotlin.coroutines.coroutineContext

class Publisher(
    private val callable: Designator,
    private val identifier: Issuer = Issuer(callable.javaClass.simpleName, callable.identifier),
) {
    private val logger = LogManager.getFormatterLogger(this.javaClass)

    @ObsoleteCoroutinesApi
    fun commit() {
        if (identifier.getJob() == null || identifier.getJob()!!.isCompleted) {
            val context = newSingleThreadContext(identifier.ident)
            val job = GlobalScope.launch(context) { callable.launchJob() }
            identifier.setJob(job)
            logger.info("${identifier.type}${identifier.ident} subscribe ✓ ${job.isActive}")
        }
    }
}
