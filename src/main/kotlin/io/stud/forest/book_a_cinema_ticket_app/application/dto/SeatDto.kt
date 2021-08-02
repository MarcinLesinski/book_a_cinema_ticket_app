package io.stud.forest.book_a_cinema_ticket_app.application.dto

import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.Id
import io.stud.forest.book_a_cinema_ticket_app.domain.RoomOccupancy
import io.stud.forest.book_a_cinema_ticket_app.domain.SeatNumber
import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Seat


data class SeatWriteDto(val id: Id, val row: Long = 0, val sequenceNumber: Long = 0)

fun List<SeatWriteDto>.numbers(): List<SeatNumber> = this.map{SeatNumber(it.row, it.sequenceNumber) }

data class SeatDto(val id: Id, val row: Long, val sequenceNumber: Long, val isFree: Boolean = true) {

    companion object {
        fun listOf(room: RoomOccupancy): List<SeatDto> {
            return room
                    .seats
                    .map { SeatDto(it.id, it.seat.row, it.seat.sequenceNumber, it.available) }
        }

        fun listOf(seats: Set<Seat>): List<SeatDto> {
            return seats.map{
                SeatDto(it.id, it.number().row, it.number().sequenceNumber)
            }
        }

    }
    /**
     * in future it may be needed to split [SeatDto] into two separated Dtos:
     * [SeatDto] is used in two cases:
     *  - to return availability of room seats before reservation
     *  - to return booked seats in reservation confirmation
     */
}

