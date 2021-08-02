package io.stud.forest.book_a_cinema_ticket_app.domain.entities

import io.stud.forest.jpa.FixedIdEntity
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "voucher_codes")
class VoucherCode(
        val code: String
) : FixedIdEntity() {


    @Column(name = "flat_discount_for_entire_order")
    var flatDiscountForEntireOrder: BigDecimal = BigDecimal.ZERO

    @Column(name = "percentage_discount_for_entire_order")
    var percentageDiscountForEntireOrder: BigDecimal = BigDecimal.ZERO

    companion object{
        fun flatDiscount(code: String, discountValue: BigDecimal): VoucherCode{
            return VoucherCode(code).apply {
                this.flatDiscountForEntireOrder = discountValue
            }
        }

        fun percentageDiscount(code: String, discountValue: BigDecimal): VoucherCode{
            return VoucherCode(code).apply{
                this.percentageDiscountForEntireOrder = discountValue
            }
        }

    }

}