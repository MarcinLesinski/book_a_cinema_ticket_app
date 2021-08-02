package io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.policy

import io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.BookingPricePolicy
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.discount.Discount
import java.math.BigDecimal

class CombinedPolicy(
        private val policy: BookingPricePolicy,
        private val discount: Discount
): BookingPricePolicy {
    override fun totalAmountToPay(): BigDecimal {
        return discount.include(policy.totalAmountToPay())
    }
}