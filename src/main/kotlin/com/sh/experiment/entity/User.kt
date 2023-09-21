package com.sh.experiment.entity

import io.quarkus.mongodb.panache.common.MongoEntity
import io.quarkus.mongodb.panache.kotlin.reactive.ReactivePanacheMongoCompanion
import io.quarkus.mongodb.panache.kotlin.reactive.ReactivePanacheMongoEntity
import io.smallrye.mutiny.Uni
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import org.eclipse.microprofile.metrics.MetricUnits
import org.eclipse.microprofile.metrics.annotation.Timed
import java.time.LocalDate

@MongoEntity(collection = "users")
data class User @BsonCreator constructor(
    @BsonProperty("email") var email: String,
    @BsonProperty("password") var password: String,
    @BsonProperty("name") var name: String,
    @get:BsonProperty("birth_date") @param:BsonProperty("birth_date") var birthDate: LocalDate,
    @BsonProperty("status") var status: Status = Status.ACTIVE
) : ReactivePanacheMongoEntity() {
    companion object : ReactivePanacheMongoCompanion<User> {
        fun findUserById(id: ObjectId): Uni<User?> = findById(id)

        @Timed(name = "db_user_add", unit = MetricUnits.MILLISECONDS)
        fun saveUser(user: User) = user.persist<User>()

        @Timed(name = "db_user_delete", unit = MetricUnits.MILLISECONDS)
        fun deleteUser(id: ObjectId) = deleteById(id)

        fun findUserByEmail(email: String) = find("email", email).firstResult()

        fun findUsersByStatus(status: Status) = find("status", status).list()
    }
}

enum class Status {
    ACTIVE, INACTIVE, BLOCKED
}
