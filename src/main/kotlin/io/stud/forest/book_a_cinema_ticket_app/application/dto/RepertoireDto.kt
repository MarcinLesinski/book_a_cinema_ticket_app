package io.stud.forest.book_a_cinema_ticket_app.application.dto


import com.fasterxml.jackson.annotation.JsonPropertyOrder
import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Screening
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.Id
import java.time.LocalDateTime

@JsonPropertyOrder("movieTitle", "timetable")
data class RepertoireDto(
        val movieTitle: String,
        val timetable: List<TimeTableItem>,
) {
    companion object {
        fun listOf(screenings: List<Screening>): List<RepertoireDto> =
                screenings
                        .groupBy { it.movie.title }
                        .map(sortEachGroupByStartTime)
                        .map(mapGroupToDto)
                        .sortedBy { it.movieTitle }

        private val mapGroupToDto: (Map.Entry<String, List<Screening>>) -> RepertoireDto = { titleToScreenings ->
            RepertoireDto(
                    titleToScreenings.key,
                    titleToScreenings.value.map {
                        TimeTableItem(
                                it.id,
                                concatScreeningDateAndTime(it))
                    })
        }

        private val concatScreeningDateAndTime: (Screening) -> LocalDateTime = { it -> LocalDateTime.of(it.day, it.startTime) }
        private val sortEachGroupByStartTime: (Map.Entry<String, List<Screening>>) -> Map.Entry<String, List<Screening>> = { group ->
            group.value.sortedBy { it.startTime }.let { group }
        }
    }
}

@JsonPropertyOrder("screeningId", "time")
data class TimeTableItem(
        val screeningId: Id,
        val time: LocalDateTime,
)