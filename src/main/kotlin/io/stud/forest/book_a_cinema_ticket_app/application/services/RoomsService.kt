package io.stud.forest.book_a_cinema_ticket_app.application.services

import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Reservation
import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Room
import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Screening
import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Seat
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.ReservationRepository
import io.stud.forest.book_a_cinema_ticket_app.domain.RoomOccupancy
import io.stud.forest.book_a_cinema_ticket_app.domain.SeatNumber
import io.stud.forest.book_a_cinema_ticket_app.domain.SeatOccupancy
import org.springframework.stereotype.Service
import org.springframework.web.context.annotation.RequestScope

@Service
class RoomsService(
        private val reservationRepository: ReservationRepository,
) {
    fun getOccupancy(screening: Screening): RoomOccupancy {
        val reservations = reservationRepository.findByScreeningId(screening.id)
        val seatsOccupancy = SeatsOccupancyService(reservations).getFor(screening.room)

        return RoomOccupancy(seatsOccupancy)
    }
}

private class SeatsOccupancyService(
        reservations: List<Reservation>,
) {
    init {

        require(reservations.distinctBy { it.screening.id }.size in setOf(1, 0)) {
            error("require reservation for only one or zero screenings") //TODO: custom exception
        }
    }

    private val takenSeats: List<Seat> = reservations.map { it.seats }.flatten().distinct()
    private val takenSeatNumbers: List<SeatNumber> = takenSeats.map { it.number() }

    fun getFor(room: Room): List<SeatOccupancy> {
        return room.seats.map {
            SeatOccupancy(it.id, it.number(), takenSeatNumbers.contains(it.number()).not())
        }
    }
}