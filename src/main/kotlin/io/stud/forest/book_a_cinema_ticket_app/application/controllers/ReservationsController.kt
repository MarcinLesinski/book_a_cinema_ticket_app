package io.stud.forest.book_a_cinema_ticket_app.application.controllers

import io.stud.forest.book_a_cinema_ticket_app.application.dto.ReservationReadDto
import io.stud.forest.book_a_cinema_ticket_app.application.dto.ReservationWriteDto
import io.stud.forest.book_a_cinema_ticket_app.application.services.BookingService
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.Id
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


@RestController
@RequestMapping("/reservations")
@Validated
class ReservationsController(
        private val bookingService: BookingService,
        @Value("\${properties.links.reservation_confirmation}")
        private val confirmationLinkPattern: String,
) {
    @PostMapping("")
    fun createReservation(
            @Valid @RequestBody reservation: ReservationWriteDto
    ): ResponseEntity<ReservationReadDto> {
        return bookingService
                .book(reservation)
                .apply{ confirmationLink = confirmationLinkPattern.format(reservationId) }
                .let { ResponseEntity.ok(it) }
    }

    @GetMapping("/{reservation_id}/confirm")
    fun confirmReservation(@PathVariable("reservation_id") reservationId: Id): ResponseEntity<Void>{
        bookingService.confirm(reservationId)
        return ResponseEntity.noContent().build()
    }

}
