package com.sh.experiment

import io.quarkus.mongodb.panache.kotlin.reactive.ReactivePanacheMongoRepositoryBase
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.bson.types.ObjectId

@ApplicationScoped
class UserRepository : ReactivePanacheMongoRepositoryBase<User, ObjectId> {
    fun findUserById(id: ObjectId): Uni<User?>? = findById(id)

    fun saveUser(user: User) = persist(user)

    fun deleteUser(id: ObjectId) = deleteById(id)

    fun findUserByEmail(email: String) = find("email", email).firstResult()

    fun findUsersByStatus(status: Status) = find("status", status).list()
}
