package io.stud.forest.book_a_cinema_ticket_app.domain.policy.seats_validation

import io.stud.forest.book_a_cinema_ticket_app.domain.SeatNumber
import io.stud.forest.book_a_cinema_ticket_app.domain.SeatOccupancy
import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Seat
import java.util.*

fun takenSeat(row: Long, number: Long) = SeatOccupancy(id(), SeatNumber(row, number), false)
fun freeSeat(row: Long, number: Long) = SeatOccupancy(id(), SeatNumber(row, number), true)
fun seatNo(row: Long, number: Long) = SeatNumber(row, number)
fun seat(row: Long, number: Long) = Seat(row, number)
private fun id() = UUID.randomUUID()