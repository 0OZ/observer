[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=0OZ_observer&metric=alert_status)](https://sonarcloud.io/dashboard?id=0OZ_observer)

## Loop:

the idea is that the observer processes a list of jobs or classes. without starting one of them twice. in a defined
interval this action is repeated.

```kotlin
class Runner() : Launcher() {
    private var eventList: List<String> = repository.findAll()

    override fun start() {
        addEventLoops {
            event()
        }
    }

    // write own events
    fun CoroutineScope.event() = launch {
        while (runEventLoop) {
            delay(60_000)
            // do something
        }
    }

    // add jobs to default event loop
    override fun CoroutineScope.addEvent() = launch {
        launchJobs(eventList) { Event(it) }
    }
}


class Event() : Designator {
    override val identifier = "identifier123"

    override fun start() {
        // TODO("do something cpu intensive")
    }
}


```


