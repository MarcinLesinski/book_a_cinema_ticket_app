package io.stud.forest.book_a_cinema_ticket_app.application.services

import io.stud.forest.book_a_cinema_ticket_app.application.dto.TicketTypeDto
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.TicketTypeRepository
import org.springframework.stereotype.Service
import org.springframework.web.context.annotation.SessionScope

@Service
class TicketsService(
        private val ticketTypeRepository: TicketTypeRepository
) {
    fun getAllCurrentTicketTypes(): List<TicketTypeDto>{
        return TicketTypeDto.listOf(ticketTypeRepository.findAll())
    }
}