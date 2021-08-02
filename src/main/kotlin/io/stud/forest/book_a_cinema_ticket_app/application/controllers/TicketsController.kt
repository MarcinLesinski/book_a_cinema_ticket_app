package io.stud.forest.book_a_cinema_ticket_app.application.controllers

import io.stud.forest.book_a_cinema_ticket_app.application.services.TicketsService
import io.stud.forest.book_a_cinema_ticket_app.application.dto.TicketTypeDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tickets")
class TicketsController(
        private val ticketsService: TicketsService
) {

    @GetMapping
    fun allCurrentAvailable(): ResponseEntity<List<TicketTypeDto>>{
        return ResponseEntity.ok(ticketsService.getAllCurrentTicketTypes())
    }
}