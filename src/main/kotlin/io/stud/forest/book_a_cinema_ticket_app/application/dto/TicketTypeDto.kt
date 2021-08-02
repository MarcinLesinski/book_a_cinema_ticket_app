package io.stud.forest.book_a_cinema_ticket_app.application.dto


import io.stud.forest.book_a_cinema_ticket_app.domain.entities.TicketType
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.Id
import java.text.NumberFormat

data class TicketTypeDto(
        val id: Id,
        val name: String,
        val price: String,
        val description: String
){
    companion object {
        fun of(ticketType: TicketType): TicketTypeDto {
            return TicketTypeDto(
                   ticketType.id,
                    ticketType.name,
                    NumberFormat.getCurrencyInstance().format(ticketType.price),
                    ticketType.purchaseCondition
            )
        }

        fun listOf(ticketTypes: List<TicketType>): List<TicketTypeDto> {
            return ticketTypes.map (TicketTypeDto::of)
        }
    }
}