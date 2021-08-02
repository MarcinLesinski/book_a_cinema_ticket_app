package io.stud.forest.book_a_cinema_ticket_app.application.dto

import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Screening
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.Id
import io.stud.forest.book_a_cinema_ticket_app.domain.RoomOccupancy
import java.time.LocalDate
import java.time.LocalTime

data class ScreeningDto(
        val roomId: Id,
        val movieId: Id,
        val day: LocalDate,
        val startTime: LocalTime,
        val seats: List<SeatDto>,
) {

    companion object {
        fun of(screening: Screening, room: RoomOccupancy): ScreeningDto =
                ScreeningDto(
                        screening.room.id,
                        screening.movie.id,
                        screening.day,
                        screening.startTime,
                        SeatDto.listOf(room)
                )
    }

}


