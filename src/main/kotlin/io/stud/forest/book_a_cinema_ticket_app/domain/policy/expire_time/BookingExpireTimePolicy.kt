package io.stud.forest.book_a_cinema_ticket_app.domain.policy.expire_time

import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Screening
import java.time.LocalDateTime

interface BookingExpireTimePolicy {
    fun expireTime(screening: Screening): LocalDateTime
}
