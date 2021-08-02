package io.stud.forest.book_a_cinema_ticket_app.domain.repositories

import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Screening
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalTime
import org.springframework.data.repository.findByIdOrNull as findByIdOrNullOriginal
import java.util.*



@Repository
interface ScreeningRepository: JpaRepository<Screening, Id>{
    @EntityGraph(attributePaths = ["movie", "room"])
    fun findByDayAndStartTimeBetween(day: LocalDate, startTime: LocalTime, endTime: LocalTime): List<Screening>

    @EntityGraph(attributePaths = ["movie", "room"])
    override fun findById(id: Id): Optional<Screening>
}

fun ScreeningRepository.findByIdOrNull(id: Id): Screening? = this.findById(id).orElse(null)

