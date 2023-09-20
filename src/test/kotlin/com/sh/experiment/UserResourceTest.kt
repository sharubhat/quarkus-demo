package com.sh.experiment

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test
import java.time.LocalDate

@QuarkusTest
class UserResourceTest {

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
}
