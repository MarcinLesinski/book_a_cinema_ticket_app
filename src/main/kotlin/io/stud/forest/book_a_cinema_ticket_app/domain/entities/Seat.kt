package io.stud.forest.book_a_cinema_ticket_app.domain.entities

import io.stud.forest.book_a_cinema_ticket_app.domain.SeatNumber
import io.stud.forest.jpa.FixedIdEntity

import javax.persistence.*

@Entity
@Table(name = "seats")
class Seat(
        private val row: Long,
        @Column(name = "sequence_number")
        private val sequenceNumber: Long,

        @Column(name = "disability_friendly")
        val disabilityFriendly: Boolean = false,
) : FixedIdEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")//, referencedColumnName = "id")
    lateinit var room: Room

    fun number(): SeatNumber{
        return SeatNumber(row, sequenceNumber)
    }

}