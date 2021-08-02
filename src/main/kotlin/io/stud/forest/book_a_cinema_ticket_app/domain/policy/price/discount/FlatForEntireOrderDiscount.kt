package io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.discount

import java.math.BigDecimal

class FlatForEntireOrderDiscount(
        private val discountValue: BigDecimal
): Discount {
    override fun include(value: BigDecimal): BigDecimal{
        return value.minus(discountValue)
    }
}