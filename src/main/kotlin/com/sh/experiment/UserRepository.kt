package com.sh.experiment

import io.quarkus.mongodb.panache.kotlin.reactive.ReactivePanacheMongoRepositoryBase
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.bson.types.ObjectId
import org.eclipse.microprofile.metrics.MetricUnits
import org.eclipse.microprofile.metrics.annotation.Timed

@ApplicationScoped
class UserRepository : ReactivePanacheMongoRepositoryBase<User, ObjectId> {
    fun findUserById(id: ObjectId): Uni<User?>? = findById(id)

    @Timed(name = "db_user_add", unit = MetricUnits.MILLISECONDS)
    fun saveUser(user: User) = persist(user)

    @Timed(name = "db_user_delete", unit = MetricUnits.MILLISECONDS)
    fun deleteUser(id: ObjectId) = deleteById(id)

    fun findUserByEmail(email: String) = find("email", email).firstResult()

    fun findUsersByStatus(status: Status) = find("status", status).list()
}
