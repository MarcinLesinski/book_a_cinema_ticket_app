package io.stud.forest.book_a_cinema_ticket_app.domain.repositories

import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Seat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SeatRepository: JpaRepository<Seat, Id> {
}