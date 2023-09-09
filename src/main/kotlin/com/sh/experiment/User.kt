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
open class User {
    @Id
    @SequenceGenerator(name = "userSequence", sequenceName = "User_SEQ", allocationSize = 1, initialValue = 4)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "User_SEQ")
    open var id: Int? = null

    @Column
    @NotNull
    open lateinit var email: String

    @Column
    @NotNull
    open lateinit var password: String

    @Column
    open lateinit var name: String

    @Column(name = "birth_date")
    @PastOrPresent
    @NotNull
    open lateinit var birthDate: LocalDate

    @Column
    @Enumerated(EnumType.STRING)
    open var status: Status = Status.ACTIVE

}

enum class Status {
    ACTIVE, INACTIVE, BLOCKED
}
