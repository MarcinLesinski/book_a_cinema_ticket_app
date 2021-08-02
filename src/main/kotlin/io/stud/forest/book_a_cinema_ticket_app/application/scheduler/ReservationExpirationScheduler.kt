package io.stud.forest.book_a_cinema_ticket_app.application.scheduler

import io.stud.forest.book_a_cinema_ticket_app.application.services.BookingService
import io.stud.forest.book_a_cinema_ticket_app.application.services.expiration.ExpirationService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ReservationExpirationScheduler(
        private val expirationService: ExpirationService
) {
    @Scheduled(fixedDelay = 15 * 1000)
    fun run() {
        expirationService.expireOutdatedReservations()
    }
}