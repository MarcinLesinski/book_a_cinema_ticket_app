package io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.policy

import io.stud.forest.book_a_cinema_ticket_app.domain.entities.TicketType
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.BookingPricePolicy
import java.math.BigDecimal

class WeekendPricesPolicy (
        private val bookedTickets: List<TicketType>
): BookingPricePolicy {
    override fun totalAmountToPay(): BigDecimal {

        return bookedTickets
                .map{it.price}
                .map{it.add(WEEKEND_MARGIN)}
                .fold(BigDecimal.ZERO, BigDecimal::add)
    }

    companion object{
        val WEEKEND_MARGIN = BigDecimal("4")
    }
}