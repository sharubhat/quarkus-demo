package com.sh.experiment.service

import arrow.core.Either
import com.sh.experiment.entity.Status
import com.sh.experiment.entity.User
import io.smallrye.mutiny.coroutines.awaitSuspending
import jakarta.enterprise.context.ApplicationScoped
import org.bson.types.ObjectId

@ApplicationScoped
class UserService {
    suspend fun saveUser(user: User): Either<Throwable, User> =
        Either.catch { User.saveUser(user).awaitSuspending().hidePassword() }.mapLeft { it }

    suspend fun deleteUser(id: ObjectId) =
        User.deleteUser(id).awaitSuspending()

    suspend fun findUserById(id: ObjectId) =
        User.findUserById(id).awaitSuspending()?.hidePassword()

    suspend fun findUserByEmail(email: String) =
        User.findUserByEmail(email).awaitSuspending()?.hidePassword()

    suspend fun findUsersByStatus(status: String) =
        User.findUsersByStatus(Status.valueOf(status)).awaitSuspending().map { it.hidePassword() }

    // extension function to hide password in the response
    private fun User.hidePassword(): User {
        val user = this.copy(password = "*****")
        user.id = this.id
        return user
    }
}
