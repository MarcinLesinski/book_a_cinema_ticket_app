package io.stud.forest.book_a_cinema_ticket_app.domain.policy.price


import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Screening
import io.stud.forest.book_a_cinema_ticket_app.domain.entities.TicketType
import io.stud.forest.book_a_cinema_ticket_app.domain.entities.VoucherCode
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.policy.CombinedPolicy
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.policy.StandardPricePolicy
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.policy.WeekendPricesPolicy
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.rule.WeekendRule
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.VoucherCodeRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.invocation.InvocationOnMock
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.time.LocalDate
import java.time.LocalTime

//TODO: add scenarios to cover CombinedPolicy versions
internal class BookingPricePolicyFactoryTest {

    private lateinit var voucherRepository: VoucherCodeRepository
    private lateinit var weekendRule: WeekendRule
    private lateinit var pricePolicyFactory: BookingPricePolicyFactory
    private lateinit var screening: Screening
    private val tickets = listOf<TicketType>()
    private val VALID_CODE = "ValidVoucherCode"
    private val INVALID_CODE = "InvalidVoucherCode"

    @BeforeEach
    fun beforeEach() {
        voucherRepository = mock()

        doAnswer(findByCodeAnswer).`when`(voucherRepository).findByCode(anyString())
        weekendRule = mock()
        pricePolicyFactory = BookingPricePolicyFactory(voucherRepository, weekendRule)
        screening = mock()
        doReturn(LocalDate.EPOCH).`when`(screening).day
        doReturn(LocalTime.MIDNIGHT).`when`(screening).startTime
    }

    @Test
    fun `should return standard policy for invalid voucher and normal day`() {
        //given
        val voucher = INVALID_CODE
        doReturn(false).`when`(weekendRule).isWeekend(any(), any())
        //when
        val policy = pricePolicyFactory.create(screening, tickets, voucher)

        //then
        assertThat(policy).isExactlyInstanceOf(StandardPricePolicy::class.java)
    }

    @Test
    fun `should return weekend policy for invalid voucher and weekend day`() {
        //given
        val voucher = INVALID_CODE
        doReturn(true).`when`(weekendRule).isWeekend(any(), any())
        //when
        val policy = pricePolicyFactory.create(screening, tickets, voucher)

        //then
        assertThat(policy).isExactlyInstanceOf(WeekendPricesPolicy::class.java)
    }

    @Test
    fun `should return combined policy for valid voucher`() {
        //given
        val voucher = VALID_CODE
        doReturn(true).`when`(weekendRule).isWeekend(any(), any())
        //when
        val policy = pricePolicyFactory.create(screening, tickets, voucher)

        //then
        assertThat(policy).isExactlyInstanceOf(CombinedPolicy::class.java)
    }


    val findByCodeAnswer = { invocation: InvocationOnMock ->
        val code = invocation.getArgument<String>(0)
        if (code == VALID_CODE)
            VoucherCode(VALID_CODE)
        else
            null
    }

}