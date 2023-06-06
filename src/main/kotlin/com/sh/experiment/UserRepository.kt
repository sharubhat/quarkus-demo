package com.sh.experiment

import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserRepository: PanacheRepositoryBase<User, Int> {
    fun findByEmail(email: String) = find("email", email).firstResult()
    fun findByStatus(status: Status) = find("status", status).list()
}
