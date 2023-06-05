package com.sh.experiment

import io.quarkus.hibernate.reactive.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntityBase
import jakarta.enterprise.context.ApplicationScoped
import java.time.LocalDate
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PastOrPresent


@Entity
@Table(name = "\"User\"")
class User: PanacheEntityBase {
    companion object: PanacheCompanion<User> {
        fun findByEmail(email: String) = find("email", email).firstResult()
        fun findByStatus(status: Status) = find("status", status).list()
    }

    @Id
    @SequenceGenerator(name = "userSequence", sequenceName = "User_SEQ", allocationSize = 1, initialValue = 4)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "User_SEQ")
    var id: Int? = null

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
