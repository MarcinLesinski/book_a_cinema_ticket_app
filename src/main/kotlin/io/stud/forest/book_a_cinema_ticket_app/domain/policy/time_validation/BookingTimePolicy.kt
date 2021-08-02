package io.stud.forest.book_a_cinema_ticket_app.domain.policy.time_validation

import io.stud.forest.book_a_cinema_ticket_app.domain.policy.ValidationPolicyResult

interface BookingTimePolicy {
    fun check(): ValidationPolicyResult
}