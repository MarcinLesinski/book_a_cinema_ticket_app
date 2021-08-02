package io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.policy

import io.stud.forest.book_a_cinema_ticket_app.domain.entities.TicketType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import utils.syntax_sugar.arguments
import utils.syntax_sugar.of
import java.math.BigDecimal

internal class WeekendPricesPolicyTest {

    @ParameterizedTest
    @MethodSource("expected | prices")
    fun `should add 4_00 to every ticket price and then sum up prices`(
            expectedTotalAmountToPay: String, prices: Array<String>,
    ) {
        //given

        val policy = WeekendPricesPolicy(ticketsWithPrices(prices))

        //when
        val totalAmountToPay = policy.totalAmountToPay()

        //then
        Assertions.assertThat(totalAmountToPay).isEqualByComparingTo(BigDecimal(expectedTotalAmountToPay))
    }

    companion object {
        fun ticket(price: String): TicketType = TicketType("", BigDecimal(price))
        fun ticketsWithPrices(prices: Array<String>): List<TicketType> = prices.map(::ticket)

        @JvmStatic
        fun `expected | prices`() = arguments(
                of("0.00", arrayOf<String>()),
                of("9.00", arrayOf("5.00")),
                of("9.10", arrayOf("0.74", "0.36")),
                of("24.75", arrayOf("1.00", "1.00", "1.00", "1.50", "0.25")),
        )
    }
}