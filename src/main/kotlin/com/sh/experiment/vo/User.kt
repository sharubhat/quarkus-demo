package com.sh.experiment.vo

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.sh.experiment.entity.Status
import com.sh.experiment.entity.UserEntity
import org.bson.types.ObjectId
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class User(
    @field:JsonProperty("id") val id: ObjectId? = null,
    @field:JsonProperty("email") val email: String,
    @field:JsonProperty("password") val password: String,
    @field:JsonProperty("name") val name: String,
    @field:JsonProperty("birthDate") val birthDate: LocalDate? = null,
    @field:JsonProperty("status") var status: String = "ACTIVE"
) {
    companion object {
        fun getUserFromEntity(user: UserEntity): User =
            User(
                id = user.id,
                email = user.email,
                password = user.password,
                name = user.name,
                birthDate = user.birthDate,
                status = user.status.name
            )

        fun toUserEntity(user: User): UserEntity =
            UserEntity(
                email = user.email,
                password = user.password,
                name = user.name,
                birthDate = user.birthDate,
                status = Status.valueOf(user.status)
            )
    }
}
