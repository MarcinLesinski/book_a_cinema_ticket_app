package io.stud.forest.book_a_cinema_ticket_app.domain.policy.expire_time

import org.springframework.stereotype.Component

@Component
class BookingExpireTimePolicyFactory {

    fun create(): BookingExpireTimePolicy {
        return Expire15MinutesBeforeScreeningPolicy()
    }
}