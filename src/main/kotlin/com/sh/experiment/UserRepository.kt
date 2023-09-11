package com.sh.experiment

import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserRepository : PanacheRepositoryBase<User, Int> {
    @WithTransaction
    fun findUserById(id: Int): Uni<User>? = findById(id)

    @WithTransaction
    fun saveUser(user: User) = persist(user)

    @WithTransaction
    fun deleteUser(id: Int) = deleteById(id)

    @WithTransaction
    fun findUserByEmail(email: String) = find("email", email).firstResult()

    @WithTransaction
    fun findUsersByStatus(status: Status) = find("status", status).list()
}
