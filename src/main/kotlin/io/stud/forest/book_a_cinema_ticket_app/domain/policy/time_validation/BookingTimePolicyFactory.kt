package io.stud.forest.book_a_cinema_ticket_app.domain.policy.time_validation

import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Screening
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class BookingTimePolicyFactory {
    fun create(
            screening: Screening,
            timeProvider: () -> LocalDateTime,
    ): BookingTimePolicy
    {
        return  EarlierThanScreeningStartsBy15MinutesPolicy(screening, timeProvider)
    }
}
