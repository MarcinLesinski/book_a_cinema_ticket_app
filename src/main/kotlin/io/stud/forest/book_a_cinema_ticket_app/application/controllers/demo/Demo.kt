package io.stud.forest.book_a_cinema_ticket_app.application.controllers.demo

import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Movie
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.MovieRepository
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.ReservationRepository
import org.springframework.context.annotation.Profile
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.transaction.Transactional

@RestController
@Profile("demo")
@RequestMapping("/demo")
class DemoController(
        private val reservationRepository: ReservationRepository,
        private val movieRepository: MovieRepository
) {
    @DeleteMapping("/reservations")
    @Transactional
    fun clearReservations(): ResponseEntity<Any>{
        reservationRepository.deleteAll()
        return ResponseEntity.noContent().build()
    }
}