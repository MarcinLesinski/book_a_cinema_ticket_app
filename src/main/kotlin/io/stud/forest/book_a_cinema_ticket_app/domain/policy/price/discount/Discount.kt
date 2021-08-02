package io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.discount

import java.math.BigDecimal

interface Discount {
    fun include(value: BigDecimal): BigDecimal
}