package io.stud.forest.book_a_cinema_ticket_app.domain

import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.Id

data class SeatOccupancy(
        val id: Id,
        val seat: SeatNumber,
        val available: Boolean,
        val disabilityFriendly: Boolean = false){}