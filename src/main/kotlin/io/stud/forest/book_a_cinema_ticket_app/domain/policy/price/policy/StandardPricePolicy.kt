package io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.policy

import io.stud.forest.book_a_cinema_ticket_app.domain.entities.TicketType
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.BookingPricePolicy
import java.math.BigDecimal

class StandardPricePolicy(
        private val bookedTickets: List<TicketType>,
): BookingPricePolicy {
    override fun totalAmountToPay(): BigDecimal {
        return bookedTickets.map{it.price}.fold(BigDecimal.ZERO, BigDecimal::add)
    }
}