package io.stud.forest.book_a_cinema_ticket_app.domain.policy.price

import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Screening
import io.stud.forest.book_a_cinema_ticket_app.domain.entities.TicketType
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.discount.FlatForEntireOrderDiscount
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.discount.PercentageForEntireOrderDiscount
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.policy.CombinedPolicy
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.policy.StandardPricePolicy
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.policy.WeekendPricesPolicy
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.rule.WeekendRule
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.VoucherCodeRepository
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class BookingPricePolicyFactory(
        private val voucherCodeRepository: VoucherCodeRepository,
        private val weekendRule: WeekendRule
) {

    fun create(screening: Screening, tickets: List<TicketType>, voucherCode: String): BookingPricePolicy {
        val isWeekendNow = weekendRule.isWeekend(screening.day, screening.startTime);
        val voucher = voucherCodeRepository.findByCode(voucherCode)

        val discount = voucher?.let {
            val percentageDiscount = it.percentageDiscountForEntireOrder.compareTo(BigDecimal.ZERO) != 0
            if (percentageDiscount)
                PercentageForEntireOrderDiscount(voucher.percentageDiscountForEntireOrder)
            else
                FlatForEntireOrderDiscount(voucher.flatDiscountForEntireOrder)
        }

        val policy = if (isWeekendNow)
            WeekendPricesPolicy(tickets)
        else
            StandardPricePolicy(tickets)


        return if (discount == null)
            policy
        else
            CombinedPolicy(policy, discount)
    }
}
