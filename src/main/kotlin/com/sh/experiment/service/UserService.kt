package com.sh.experiment.service

import com.sh.experiment.entity.User
import com.sh.experiment.entity.Status
import io.smallrye.mutiny.coroutines.awaitSuspending
import jakarta.enterprise.context.ApplicationScoped
import org.bson.types.ObjectId

@ApplicationScoped
class UserService {
    suspend fun saveUser(user: User) =
        User.saveUser(user).awaitSuspending()

    suspend fun deleteUser(id: ObjectId) =
        User.deleteUser(id).awaitSuspending()

    suspend fun findUserById(id: ObjectId) =
        User.findUserById(id).awaitSuspending()

    suspend fun findUserByEmail(email: String) =
        User.findUserByEmail(email).awaitSuspending()

    suspend fun findUsersByStatus(status: String) =
        User.findUsersByStatus(Status.valueOf(status)).awaitSuspending()
}
