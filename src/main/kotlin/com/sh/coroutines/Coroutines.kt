package com.sh.coroutines

import com.sh.experiment.UserResource
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

val logger: org.jboss.logging.Logger = org.jboss.logging.Logger.getLogger(UserResource::class.java)

suspend fun bathTime() {
    logger.info("[" + Thread.currentThread().name + "] " + "Going to the bathroom")
    delay(500)
    logger.info("[" + Thread.currentThread().name + "] " + "Bath done, exiting")
}

suspend fun boilingWater() {
    logger.info("[" + Thread.currentThread().name + "] " + "Boiling water")
    delay(1000)
    logger.info("[" + Thread.currentThread().name + "] " + "Water boiled")
}

suspend fun sequentialMorningRoutine() {
    coroutineScope {
        bathTime()
    }
    coroutineScope {
        boilingWater()
    }
}

suspend fun main() {
    sequentialMorningRoutine()
}
