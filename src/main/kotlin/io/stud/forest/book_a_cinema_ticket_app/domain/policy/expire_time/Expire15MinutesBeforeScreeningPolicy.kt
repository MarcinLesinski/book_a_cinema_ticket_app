package io.stud.forest.book_a_cinema_ticket_app.domain.policy.expire_time

import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Screening
import java.time.LocalDateTime

class Expire15MinutesBeforeScreeningPolicy: BookingExpireTimePolicy {
    override fun expireTime(screening: Screening): LocalDateTime {
        return  LocalDateTime.of(screening.day, screening.startTime).minusMinutes(15)
    }
}