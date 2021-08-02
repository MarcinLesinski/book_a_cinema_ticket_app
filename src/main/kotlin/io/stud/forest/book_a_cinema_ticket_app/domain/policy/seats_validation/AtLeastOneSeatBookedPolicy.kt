package io.stud.forest.book_a_cinema_ticket_app.domain.policy.seats_validation

import io.stud.forest.book_a_cinema_ticket_app.domain.SeatNumber
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.ValidationPolicyResult

class AtLeastOneSeatBookedPolicy(
        private val seats: List<SeatNumber>
): BookingSeatsPolicy {

    override fun check(): ValidationPolicyResult {
        val isValid = seats.isNotEmpty()
        return if (isValid)
            ValidationPolicyResult.success()
        else
            ValidationPolicyResult.error("Reservation have to contains at least one seat.")
    }
}