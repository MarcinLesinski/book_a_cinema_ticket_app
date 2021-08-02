package io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.discount

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

class PercentageForEntireOrderDiscount(
        private val discountValue: BigDecimal
): Discount {
    override fun include(value: BigDecimal): BigDecimal {
        return if (discountValue.compareTo(BigDecimal.ZERO) == 0)
            value
        else
            value.minus( value.divide(BigDecimal("100")).multiply(discountValue, MathContext(2, RoundingMode.CEILING)) )
    }
}