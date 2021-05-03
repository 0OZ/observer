package io.averest.observer.domain.launcher.service

import io.averest.observer.domain.launcher.MainLoop.running
import io.averest.observer.domain.launcher.application.Publisher
import io.averest.observer.infrastructure.Accelerator
import io.averest.observer.infrastructure.Designator
import io.averest.observer.repository.JobRepository
import kotlinx.coroutines.asCoroutineDispatcher
import org.apache.logging.log4j.LogManager
import java.lang.Thread.sleep
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.concurrent.thread

abstract class Launcher(
    override val runEventLoop: Boolean = true,
    val executor: ExecutorService =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()),
    val sleepTimer: Long = 120_000L
) : Accelerator {
    val logger = LogManager.getFormatterLogger(this.javaClass)!!

    companion object {
        fun stop() {
            running = false
        }
    }

    override fun start() {
        if (!running) {
            running = runEventLoop
            mainLoop()
            optionalCall()
        }
    }


    override fun mainLoop() = thread {
        while (running) {
            executor.execute { commitJobs() }
            logger.info(JobRepository.jobs.map { mapOf(it.key to it.value.isActive) })
            sleep(sleepTimer)
        }
        executor.shutdown()
    }
    

    fun <T> commit(identList: List<T>, callable: (jobIdentifier: T) -> Designator) {
        identList.forEach { commit(callable(it)) }
    }

    private fun commit(job: Designator) = Publisher(job, executor.asCoroutineDispatcher()).commit()
}
