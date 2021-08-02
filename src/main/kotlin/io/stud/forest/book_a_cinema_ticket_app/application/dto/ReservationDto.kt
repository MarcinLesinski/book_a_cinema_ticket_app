package io.stud.forest.book_a_cinema_ticket_app.application.dto

import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Reservation
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.Id
import io.stud.forest.book_a_cinema_ticket_app.utils.ValidName
import io.stud.forest.book_a_cinema_ticket_app.utils.ValidSurName
import java.text.NumberFormat
import java.time.LocalDateTime

data class ReservationWriteDto(
        val screeningId: Id,
        @field:ValidName
        val bookerName: String,
        @field:ValidSurName
        val bookerSurname: String,
        val seats: List<Id>,
        val tickets: List<Id>,
        val voucherCode: String = ""
)

data class ReservationReadDto(
        val reservationId: Id,
        val seats: List<SeatDto>,
        val bookerName: String,
        val bookerLastName: String,
        val totalAmountToPay: String,
        val expireTime: LocalDateTime,
) {
        lateinit var confirmationLink: String

        companion object {
                fun of(reservation: Reservation): ReservationReadDto = ReservationReadDto(
                        reservation.id,
                        SeatDto.listOf(reservation.seats),
                        reservation.bookerName,
                        reservation.bookerSurname,
                        NumberFormat.getCurrencyInstance().format(reservation.totalAmountToPay),
                        reservation.expireTime
                )
        }
}

