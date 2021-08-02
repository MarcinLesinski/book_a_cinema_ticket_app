package io.stud.forest.book_a_cinema_ticket_app.domain.entities

import io.stud.forest.jpa.FixedIdEntity
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "ticket_types")
class TicketType(
        val name: String,
        val price: BigDecimal,
        @Column(name = "purchase_condition")
        val purchaseCondition: String = ""
): FixedIdEntity() {

}