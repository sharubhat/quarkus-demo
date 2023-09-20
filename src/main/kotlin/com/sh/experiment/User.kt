package com.sh.experiment

import io.quarkus.mongodb.panache.common.MongoEntity
import io.quarkus.mongodb.panache.kotlin.reactive.ReactivePanacheMongoEntity
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonProperty
import java.time.LocalDate

@MongoEntity(collection = "users")
data class User @BsonCreator constructor(
    @BsonProperty("email") var email: String,
    @BsonProperty("password") var password: String,
    @BsonProperty("name") var name: String,
    @get:BsonProperty("birth_date") @param:BsonProperty("birth_date") var birthDate: LocalDate,
    @BsonProperty("status") var status: Status = Status.ACTIVE
): ReactivePanacheMongoEntity()

enum class Status {
    ACTIVE, INACTIVE, BLOCKED
}
