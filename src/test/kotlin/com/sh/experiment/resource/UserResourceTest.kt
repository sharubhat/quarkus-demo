package com.sh.experiment.resource

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import jakarta.inject.Inject
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

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
        val user =
            """
                {
                    "email": "sharath@gmail.com",
                    "password": "local",
                    "name": "Sharath",
                    "status": "ACTIVE"
                }
            """.trimIndent()
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
