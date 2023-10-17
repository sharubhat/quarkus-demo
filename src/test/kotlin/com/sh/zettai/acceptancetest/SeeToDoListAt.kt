package com.sh.zettai.acceptancetest

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

@QuarkusTest
class SeeToDoListAt {
    @Test
    fun `List owners can see their lists`() {
        val user = "frank"
        val listName = "shopping"
        val foodToBuy = listOf("carrots", "apples", "milk")

        val path = given()
            .`when`().get("/todo/$user/$listName")
            .then()
            .statusCode(200)
            .extract()
            .jsonPath()

        assertThat(path.getString("listName.name"), `is`(equalTo(listName)))

        // Clean this up. Doesn't look right. Should be cast to List<ToDoItem>
        assertThat(
            path.getList<LinkedHashMap<String, String>>("items").map { it.get("description") },
            `is`(equalTo(foodToBuy))
        )
    }
}
