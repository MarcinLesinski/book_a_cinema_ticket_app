package io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.policy

import io.stud.forest.book_a_cinema_ticket_app.domain.entities.TicketType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import utils.syntax_sugar.*
import java.math.BigDecimal

internal class StandardPricePolicyTest {

    @ParameterizedTest
    @MethodSource("expected | prices")
    fun `should sum up prices`(expectedTotalAmountToPay: String, prices: Array<String>) {
        //given

        val policy = StandardPricePolicy(ticketsWithPrices(prices))

        //when
        val totalAmountToPay = policy.totalAmountToPay()

        //then
        assertThat(totalAmountToPay).isEqualByComparingTo(BigDecimal(expectedTotalAmountToPay))
    }

    companion object {
        fun ticket(price: String): TicketType = TicketType("", BigDecimal(price))
        fun ticketsWithPrices(prices: Array<String>): List<TicketType> = prices.map(::ticket)
        @JvmStatic
        fun `expected | prices`() = arguments(
                of("0.00", arrayOf<String>()),
                of("5.00", arrayOf("5.00")),
                of("1.10", arrayOf("0.74", "0.36")),
                of("4.75", arrayOf("1.00", "1.00", "1.00", "1.50", "0.25")),
        )
    }


}