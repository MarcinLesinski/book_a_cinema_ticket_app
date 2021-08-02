package io.stud.forest.book_a_cinema_ticket_app.domain.policy.price

import java.math.BigDecimal

interface BookingPricePolicy {
    fun totalAmountToPay(): BigDecimal
}