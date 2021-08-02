package io.stud.forest.book_a_cinema_ticket_app.domain.policy.seats_validation

import io.stud.forest.book_a_cinema_ticket_app.domain.RoomOccupancy
import io.stud.forest.book_a_cinema_ticket_app.domain.SeatNumber
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.ValidationPolicyResult

/**
 * There cannot be a single place left over in a row between two already reserved places.
 */
class NoSingleSeatLeftInRowPolicy(
        private val seatsForBooking: List<SeatNumber>,
        private val actualRoomOccupancy: RoomOccupancy,
) : BookingSeatsPolicy {

    override fun check(): ValidationPolicyResult {
        val simulation = actualRoomOccupancy.seats.filter{it.available.not()}.map { it.seat } + seatsForBooking
        val isValid = simulation
                .groupBy { it.row }
                .all { group ->
                    group
                            .value
                            .map { it.sequenceNumber }
                            .sorted()
                            .windowed(2, 1)
                            .any { (it[1] - it[0]) == 2.toLong() }
                            .not()
                }
        return if (isValid)
            ValidationPolicyResult.success()
        else
            ValidationPolicyResult.error("There cannot be a single place left over in a row between two already reserved places.")
    }

}