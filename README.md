## JobHandler:

the idea is that the observer processes a list of jobs or classes. without starting one of them twice. in a defined
interval this action is repeated.

```kotlin
class Runner(
    private val identifiers: List<String> = listOf("foo","boo")
) : Launcher(sleepTimer = 2_000L) {

    override fun commitJobs() {
        launchJobs(jobList) { Excavator(it) }
    }
}


class Job(
    override val identifier: String = "foo"
) : Designator {

    override fun start() {
        // TODO("do something intensive")
    }
}

```


