package io.stud.forest.book_a_cinema_ticket_app.application.services

import io.stud.forest.book_a_cinema_ticket_app.application.dto.ScreeningDto
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.Id
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.ScreeningRepository
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ScreeningsService(
        private val screeningRepository: ScreeningRepository,
        private val roomsService: RoomsService
) {

    fun find(screeningId: Id): ScreeningDto {
        val screening = screeningRepository.findByIdOrNull(screeningId) ?: error("screening not found")
        val room = roomsService.getOccupancy(screening)

        return ScreeningDto.of(screening, room)
    }
}

