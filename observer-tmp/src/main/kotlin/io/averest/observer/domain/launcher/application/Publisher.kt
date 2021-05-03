package io.averest.observer.domain.launcher.application

import io.averest.observer.domain.launcher.aggregate.Issuer
import io.averest.observer.infrastructure.Designator
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.apache.logging.log4j.LogManager

class Publisher(
    private val callable: Designator,
    private val executor: ExecutorCoroutineDispatcher,
    private val identifier: Issuer = Issuer(callable.javaClass.simpleName, callable.identifier),
) {
    private val logger = LogManager.getFormatterLogger(this.javaClass)
    fun commit() {
        if (identifier.getJob() == null || identifier.getJob()!!.isCompleted) {
            val job = GlobalScope.launch(executor) { callable.launchJob() }
            identifier.setJob(job)
            logger.info("${identifier.type}${identifier.ident} subscribe ✓ ${job.isActive}")
        }
    }
}
