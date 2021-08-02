package io.stud.forest.book_a_cinema_ticket_app.domain.repositories

import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Reservation
import java.util.*

class InMemoryReservationRepository: ReservationRepository{
    private val data = mutableMapOf<Id, Reservation>()

    override fun save(reservation: Reservation): Reservation {
        data[reservation.id] = reservation
        return reservation
    }


    override fun findById(id: Id): Optional<Reservation> {
        return Optional.ofNullable(data[id])
    }

    override fun findByScreeningId(screeningId: Id): List<Reservation> {
        return data.values.filter { it.screening.id == screeningId}
    }

    override fun findByConfirmed(confirmed: Boolean): List<Reservation> {
        return data.values.filter{it.confirmed == confirmed}
    }

    override fun findAll(): MutableList<Reservation> {
        return data.values.toMutableList()
    }

    override fun deleteById(id: Id) {
        data.remove(id)
    }

    override fun delete(reservation: Reservation) {
        this.deleteById(reservation.id)
    }

    override fun deleteAll() {
        data.clear()
    }

}