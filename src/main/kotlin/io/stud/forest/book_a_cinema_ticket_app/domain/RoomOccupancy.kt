package io.stud.forest.book_a_cinema_ticket_app.domain

class RoomOccupancy(
        val seats: List<SeatOccupancy>
) {
    val rows: Long = seats.map { it.seat.row }.maxOrNull() ?: 0 // consider Map
    val full: Boolean = seats.all { it.available.not() }
    val disabilityFriendly: Boolean = seats.any{ it.disabilityFriendly and it.available}
}
