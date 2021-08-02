package io.stud.forest.book_a_cinema_ticket_app.domain.policy.seats_validation

import io.stud.forest.book_a_cinema_ticket_app.domain.RoomOccupancy
import io.stud.forest.book_a_cinema_ticket_app.domain.SeatNumber
import org.springframework.stereotype.Component

@Component
class BookingSeatsPolicyFactory(
) {

    fun create(
            seatsForBooking: List<SeatNumber>,
            actualRoomOccupancy: RoomOccupancy,
    ): BookingSeatsPolicy {

        return CombinedSeatsPolicy(
                AtLeastOneSeatBookedPolicy(seatsForBooking),
                NoSingleSeatLeftInRowPolicy(seatsForBooking, actualRoomOccupancy),
                CanNotBookAlreadyBookedSeatPolicy(seatsForBooking, actualRoomOccupancy),
                logic = Boolean::and
        )

    }
}
