package io.stud.forest.book_a_cinema_ticket_app.domain.policy.seats_validation

import io.stud.forest.book_a_cinema_ticket_app.domain.policy.ValidationPolicyResult

interface BookingSeatsPolicy {
    fun check(): ValidationPolicyResult
}