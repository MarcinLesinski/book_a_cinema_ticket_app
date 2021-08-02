package io.stud.forest.book_a_cinema_ticket_app.application.controllers

import io.stud.forest.book_a_cinema_ticket_app.application.services.RepertoireService
import io.stud.forest.book_a_cinema_ticket_app.application.dto.RepertoireDto
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.LocalTime

@RestController
@RequestMapping("/repertoires")
class RepertoiresController(
        private val repertoireService: RepertoireService,
) {
    data class DemoResponse(val text: String = "demo text", val number: Int = 8, val bool: Boolean = true)
    
    @GetMapping("")
    fun allInGivenInterval(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            date: LocalDate,
            @RequestParam("from-hour")
            fromHour: Int,
            @RequestParam("to-hour")
            toHour: Int,
    ): ResponseEntity<List<RepertoireDto>> {
        return repertoireService
                .findScreeningsInTimeInterval(date,
                        LocalTime.of(fromHour, 0),
                        LocalTime.of(toHour, 0))
                .let { ResponseEntity.ok(it) }
    }

}