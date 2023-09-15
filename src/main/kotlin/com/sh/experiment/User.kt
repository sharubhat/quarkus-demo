package com.sh.experiment

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import java.time.LocalDate

class User {
    @BsonId
    var id: ObjectId? = null

    lateinit var email: String

    lateinit var password: String

    lateinit var name: String

    @BsonProperty("birth_date")
    lateinit var birthDate: LocalDate

    var status: Status = Status.ACTIVE
}

enum class Status {
    ACTIVE, INACTIVE, BLOCKED
}
