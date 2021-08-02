package io.stud.forest.book_a_cinema_ticket_app.domain.policy.time_validation

import io.stud.forest.book_a_cinema_ticket_app.domain.policy.ValidationPolicyResult
import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Screening
import java.time.LocalDateTime

class EarlierThanScreeningStartsBy15MinutesPolicy(
        private val screening: Screening,
        private val getCurrentTime: () -> LocalDateTime,
): BookingTimePolicy {

    override fun check(): ValidationPolicyResult {
        val screeningStartTime = LocalDateTime.of(screening.day, screening.startTime)
        val bookingDeadline = screeningStartTime.minusMinutes(15)

        val isValid = getCurrentTime().isBefore(bookingDeadline)
        return if (isValid)
             ValidationPolicyResult.success()
        else
            ValidationPolicyResult.error(" Seats can be booked at latest 15 minutes before the screening begins.")
    }
}