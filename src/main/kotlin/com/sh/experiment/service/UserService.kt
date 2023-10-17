package com.sh.experiment.service

import arrow.core.Either
import arrow.core.EitherNel
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.zipOrAccumulate
import com.sh.experiment.entity.Status
import com.sh.experiment.entity.UserEntity
import com.sh.experiment.vo.User
import io.smallrye.mutiny.coroutines.awaitSuspending
import jakarta.enterprise.context.ApplicationScoped
import org.bson.types.ObjectId

@ApplicationScoped
class UserService {
    fun validateUser(user: User): EitherNel<String, User> = either {
        zipOrAccumulate(
            { ensure(user.name.isNotEmpty()) { "Name cannot be empty" } },
            { ensure(user.password.isNotEmpty()) { "Password cannot be empty" } },
            { ensure(user.email.isNotEmpty()) { "Email cannot be empty" } }
        ) { _, _, _ -> user }
    }

    suspend fun saveUser(user: User): Either<Throwable, UserEntity> =
        Either.catch { UserEntity.saveUser(User.toUserEntity(user)).awaitSuspending().hidePassword() }.mapLeft { it }

    suspend fun deleteUser(id: ObjectId) =
        UserEntity.deleteUser(id).awaitSuspending()

    suspend fun findUserById(id: ObjectId) =
        UserEntity.findUserById(id).awaitSuspending()?.hidePassword()

    suspend fun findUserByEmail(email: String) =
        UserEntity.findUserByEmail(email).awaitSuspending()?.hidePassword()

    suspend fun findUsersByStatus(status: String) =
        UserEntity.findUsersByStatus(Status.valueOf(status)).awaitSuspending().map { it.hidePassword() }

    // extension function to hide password in the response
    private fun UserEntity.hidePassword(): UserEntity {
        val user = this.copy(password = "*****")
        user.id = this.id
        return user
    }
}
