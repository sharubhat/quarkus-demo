package com.sh.experiment

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDate
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PastOrPresent

@Entity
@Table(name = "\"User\"")
class User: PanacheEntity() {

    companion object: PanacheCompanion<User> {
        fun findByEmail(email: String) = find("email", email).firstResult()
        fun findByStatus(status: Status) = find("status", status).list()
    }

    @Column
    @NotNull
    lateinit var email: String

    @Column
    @NotNull
    lateinit var password: String

    @Column
    lateinit var name: String

    @Column(name = "birth_date")
    @PastOrPresent
    @NotNull
    lateinit var birthDate: LocalDate

    @Column
    @Enumerated(EnumType.STRING)
    var status: Status = Status.ACTIVE

}

enum class Status {
    ACTIVE, INACTIVE, BLOCKED
}
