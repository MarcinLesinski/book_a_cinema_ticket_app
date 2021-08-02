package io.stud.forest.book_a_cinema_ticket_app.domain

import java.time.LocalTime

data class Hour(
        val value: Int
) {
    init {
        require(value in 1..23) { "hour can take values from 1 to 23" }
    }
}

data class TimeInterval(
        val beginning: Hour,
        val end: Hour,
)