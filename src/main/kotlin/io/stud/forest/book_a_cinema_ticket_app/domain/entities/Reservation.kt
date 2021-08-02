package io.stud.forest.book_a_cinema_ticket_app.domain.entities

import io.stud.forest.jpa.FixedIdEntity
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "reservations")
// Value object candidate
class Reservation(
        @Column(name = "booker_name")
        val bookerName: String,

        @Column(name = "booker_surname")
        val bookerSurname: String,

        @OneToMany(fetch = FetchType.LAZY)
        val seats: Set<Seat>,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "screening_id")
        val screening: Screening,

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(
                joinColumns = [JoinColumn(name = "reservation_id")],
                inverseJoinColumns = [JoinColumn(name = "ticket_type_id")]
        )
        val ticketTypes: Set<TicketType>,

        ) : FixedIdEntity() {

    @Column(precision = 8, scale = 2)
    var totalAmountToPay: BigDecimal = BigDecimal.ZERO

    @Column(name = "expire_time")
    var expireTime: LocalDateTime = LocalDateTime.MIN

    @Column(name = "confirmed")
    var confirmed: Boolean? = false

    @Column(name = "confirmation_expire_date_time")
    var confirmationExpireDateTime: LocalDateTime = LocalDateTime.MIN

    @PrePersist
    fun prePersist(){
        confirmationExpireDateTime = LocalDateTime.now().plusMinutes(15)
    }
    /**
     * in a situation where the expiry rules may change during reservation
     * [expireTime] and [confirmationExpireDateTime] can be changed for moment when
     * reservation has been made.
     *
     * For now, I assume that the expiry rule is set when reservation occurs.
     */
}

