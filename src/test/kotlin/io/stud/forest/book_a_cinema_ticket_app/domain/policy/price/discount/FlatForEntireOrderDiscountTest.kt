package io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.discount

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class FlatForEntireOrderDiscountTest{

    @Test
    fun `should reduce total by fixed value`() {
        //given
        val flatDiscount = FlatForEntireOrderDiscount(BigDecimal("2.25"))
        val total = BigDecimal("5.00")

        //when
        val totalAfterDiscount = flatDiscount.include(total)

        //then
        assertThat(totalAfterDiscount).isEqualByComparingTo(BigDecimal("2.75"))
    }

}