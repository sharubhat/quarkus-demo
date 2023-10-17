package com.sh.experiment.service

import com.sh.experiment.vo.User
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@QuarkusTest
class UserServiceTest {

    @Inject
    lateinit var service: UserService

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testHello() = runTest {
        val user: User = User(
            email = "sharath@gmail.com",
            password = "local",
            name = "Sharath"
        )

        Assertions.assertEquals("sharath@gmail.com", service.saveUser(user).getOrNull()?.email)
    }
}
