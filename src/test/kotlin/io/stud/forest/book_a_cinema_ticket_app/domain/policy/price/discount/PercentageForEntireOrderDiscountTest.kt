package io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.discount

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import utils.syntax_sugar.arguments
import utils.syntax_sugar.of
import java.math.BigDecimal

internal class PercentageForEntireOrderDiscountTest{

    @ParameterizedTest
    @MethodSource("total | discount | expected reduced total" )
    fun `should reduce total by percent value`(
            total: BigDecimal, discount: BigDecimal, expectedReducedTotal: BigDecimal
    ) {
        //given
        val percentageDiscount = PercentageForEntireOrderDiscount(discount)

        //when
        val reducedTotal = percentageDiscount.include(total)

        //then
        assertThat(reducedTotal).isEqualByComparingTo(expectedReducedTotal)
    }

    companion object{
        @JvmStatic
        fun`total | discount | expected reduced total`() = arguments(
                of(money("200"), percent("25"), money("150")),
                of(money("200"), percent("0"), money("200")),
                of(money("1"), percent("1"), money("0.99")),
        )

        fun money(value: String) = BigDecimal(value)
        fun percent(value: String) = BigDecimal(value)
    }

}