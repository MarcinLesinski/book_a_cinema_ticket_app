package io.stud.forest.book_a_cinema_ticket_app.domain.repositories

import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Reservation
import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Screening
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

interface ReservationRepository{

    fun save(reservation: Reservation): Reservation

    @EntityGraph(attributePaths = ["seats", "ticketTypes", "screening"])
    fun findByScreeningId(screeningId: Id): List<Reservation>

    @EntityGraph(attributePaths = ["seats", "ticketTypes", "screening", "screening.room", "screening.movie"])
    fun findByConfirmed(confirmed: Boolean): List<Reservation>

    @EntityGraph(attributePaths = ["seats", "ticketTypes", "screening", "screening.room", "screening.movie"])
    fun findAll(): MutableList<Reservation>

    @EntityGraph(attributePaths = ["seats", "ticketTypes", "screening"])
    fun findById(id: Id): Optional<Reservation>

    @Modifying
    @Query("delete from Reservation where id = :id")
    fun deleteById(@Param("id") id: Id)

    @Modifying
    @Query("delete from Reservation where id = :#{#reservation.id}")
    fun delete(@Param("reservation")reservation: Reservation)

    @Modifying
    @Query("delete from Reservation")
    fun deleteAll()
}

@Repository
interface JpaReservationRepository : ReservationRepository, JpaRepository<Reservation, Id>

fun ReservationRepository.findByIdOrNull(id: Id): Reservation? = findById(id).orElse(null)