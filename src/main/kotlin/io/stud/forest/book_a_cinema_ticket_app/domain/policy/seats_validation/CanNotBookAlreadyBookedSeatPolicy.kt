package io.stud.forest.book_a_cinema_ticket_app.domain.policy.seats_validation

import io.stud.forest.book_a_cinema_ticket_app.domain.RoomOccupancy
import io.stud.forest.book_a_cinema_ticket_app.domain.SeatNumber
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.ValidationPolicyResult

class CanNotBookAlreadyBookedSeatPolicy(
        private val seatsForBooking: List<SeatNumber>,
        private val actualRoomOccupancy: RoomOccupancy,
) : BookingSeatsPolicy {
    override fun check(): ValidationPolicyResult {
        val isValid = actualRoomOccupancy
                .seats
                .filter{it.available.not()}
                .map { it.seat }
                .all { seatsForBooking.contains(it).not() }

        return if (isValid)
            ValidationPolicyResult.success()
        else
            ValidationPolicyResult.error("You can not reserve already reserved seat")
    }
}