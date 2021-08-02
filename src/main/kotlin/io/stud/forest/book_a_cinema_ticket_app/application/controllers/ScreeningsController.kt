package io.stud.forest.book_a_cinema_ticket_app.application.controllers

import io.stud.forest.book_a_cinema_ticket_app.application.services.ScreeningsService
import io.stud.forest.book_a_cinema_ticket_app.application.dto.ScreeningDto
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.Id
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/screenings")
class ScreeningsController(
        private val screeningsService: ScreeningsService
) {
    @GetMapping("/{id}")
    fun one(@PathVariable("id") screeningId: Id): ResponseEntity<ScreeningDto> {
        return screeningsService.find(screeningId).let{ ResponseEntity.ok(it)}
    }
}