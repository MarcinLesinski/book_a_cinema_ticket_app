package io.stud.forest.book_a_cinema_ticket_app.domain.entities

import io.stud.forest.jpa.FixedIdEntity
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.persistence.*

@Entity
@Table(name = "screenings")
class Screening(
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "movie_id", referencedColumnName = "id")
        val movie: Movie,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "room_id", referencedColumnName = "id")
        var room: Room,

        var day: LocalDate,

        @Column(name = "start_time")
        var startTime: LocalTime,
) : FixedIdEntity()
{
        fun dateTime(): LocalDateTime = LocalDateTime.of(day, startTime)
}