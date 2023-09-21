package com.sh.experiment.resource

import com.sh.experiment.entity.User
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import jakarta.inject.Inject
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate

@QuarkusTest
class UserResourceTest {

    @Inject
    lateinit var resource: UserResource

    @Test
    fun testHelloEndpoint() {
        given()
            .`when`().get("/user")
            .then()
            .statusCode(200)
            .body(`is`("howdy!"))
    }

    @Test
    fun testAddUser() {
        val user = User(
            email = "sharath@gmail.com",
            password = "local",
            birthDate = LocalDate.of(2009, 10, 7),
            name = "Sharath Bhat"
        )
        given()
            .body(user).contentType(ContentType.JSON)
            .post("/user")
            .then()
            .statusCode(201)
    }

    @Test
    fun testHello() {
        Assertions.assertEquals("howdy!", resource.hello())
    }
}
