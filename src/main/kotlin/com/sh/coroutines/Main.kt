package com.sh.coroutines

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(DelicateCoroutinesApi::class)
fun main() {
    val job1 = GlobalScope.launch(start = CoroutineStart.LAZY) {
        delay(200)
        println("pong")
        delay(200)
    }

    GlobalScope.launch {
        delay(200)
        println("ping")
        job1.join()
        println("ping")
        delay(200)
    }
    Thread.sleep(1000)


    with(GlobalScope) {
        val parentJob = launch {
            delay(200)
            println("I am the parent")
            delay(200)
        }
        launch(context = parentJob) {
            delay(200)
            println("I am the child")
            delay(200)
        }
        if (parentJob.children.iterator().hasNext()) {
            println("The job has children")
        } else {
            println("The job has no children")
        }
        Thread.sleep(1000)
    }

    var isDoorOpen = false

    println("Unlocking the door... please wait!")
    GlobalScope.launch {
        delay(3000)
        isDoorOpen = true
    }

    GlobalScope.launch {
        repeat(4) {
            println("Trying to open the door...")
            delay(800)

            if (isDoorOpen) {
                println("Open the door!")
            } else {
                println("The door is still locked!")
            }
        }
    }
    Thread.sleep(5000)

    fun getUserStandard(userId: String): Person {
        Thread.sleep(1000)
        return Person(userId, "Filip")
    }

    fun getUserFromNetworkCallback(
        userId: String,
        onUserResponse: (Person?, Throwable?) -> Unit) {
        thread {
            try {
                Thread.sleep(1000)
                val person = Person(userId, "Filip")
                onUserResponse(person, null)
            } catch (error: Throwable) {
                onUserResponse(null, error)
            }

        }
        println("end")
    }

    getUserFromNetworkCallback("101") { user, error ->
        user?.run(::println)
        error?.printStackTrace()
    }
    println("main end")

    suspend fun getUserSuspend(userId: String): Person {
        delay(1000)
        return Person(userId, "Filip")
    }

    // library function that's forcing you to use a callback `onReady()`
    fun readFile(path: String, onReady: (File) -> Unit) {
        Thread.sleep(1000)
        onReady(File(path))
    }

    // wrapping above function with suspend coroutine
    suspend fun readFileSuspend(path: String): File =
        suspendCoroutine {
            readFile(path) {file ->
                it.resume(file)
            }
        }


}

data class Person(val id: String, val name: String)
