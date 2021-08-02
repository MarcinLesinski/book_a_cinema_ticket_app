package io.stud.forest.book_a_cinema_ticket_app.application.services

import io.stud.forest.book_a_cinema_ticket_app.application.dto.RepertoireDto
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.RoomRepository
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.ScreeningRepository
import org.springframework.stereotype.Service
import org.springframework.web.context.annotation.RequestScope
import org.springframework.web.context.annotation.SessionScope
import java.time.LocalDate
import java.time.LocalTime

@Service
class RepertoireService(
        private val screeningRepository: ScreeningRepository,
        private val roomRepository: RoomRepository
) {
    fun findScreeningsInTimeInterval(day: LocalDate, beginningTime: LocalTime, endTime: LocalTime): List<RepertoireDto> =
            screeningRepository
                    .findByDayAndStartTimeBetween(day, beginningTime, endTime)
                    .run(RepertoireDto::listOf)
}
