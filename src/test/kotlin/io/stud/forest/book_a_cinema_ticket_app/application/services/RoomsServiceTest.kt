package io.stud.forest.book_a_cinema_ticket_app.application.services

import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Reservation
import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Room
import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Screening
import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Seat
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.seats_validation.seat
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.ReservationRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.util.*

internal class RoomsServiceTest {

    private val uuid = UUID(1, 2)
    private lateinit var room3x3: Room
    private lateinit var screening: Screening
    private lateinit var reservationRepository: ReservationRepository

    object seats {
        val `1 1` = seat(1, 1)
        val `1 2` = seat(1, 2)
        val `1 3` = seat(1, 3)
        val `2 1` = seat(2, 1)
        val `2 2` = seat(2, 2)
        val `2 3` = seat(2, 3)
        val `3 1` = seat(3, 1)
        val `3 2` = seat(3, 2)
        val `3 3` = seat(3, 3)
    }

    @BeforeEach
    fun beforeEach() {
        room3x3 = mock<Room> {
            on { seats }.doReturn(setOf(
                    seats.`1 1`, seats.`1 2`, seats.`1 3`,
                    seats.`2 1`, seats.`2 2`, seats.`2 3`,
                    seats.`3 1`, seats.`3 2`, seats.`3 3`,
            ))
        }

        screening = mock<Screening> {
            on { this.id }.doReturn(uuid)
            on { this.room }.doReturn(room3x3)
        }
    }

    @Test
    fun `should set full when no seats left`() {
        //given
        reservationRepository = reservationRepository(
                reservation(seat(1, 1), seat(1, 2), seat(1, 3)),
                reservation(seat(2, 1), seat(2, 2)),
                reservation(seat(2, 3)),
                reservation(seat(3, 1)),
                reservation(seat(3, 2), seat(3, 3)),
        )
        val service = RoomsService(reservationRepository)

        //when
        val roomOccupancy = service.getOccupancy(screening)

        //then
        assertThat(roomOccupancy.full).isTrue
    }

    @Test
    fun `should set taken seats`() {
        //given
        reservationRepository = reservationRepository(
                reservation(seats.`1 1`),
                reservation(seats.`2 2`),
                reservation(seats.`3 3`),
        )
        val service = RoomsService(reservationRepository)

        //when
        val roomOccupancy = service.getOccupancy(screening)

        //then
        assertThat(roomOccupancy.seats.filter { it.available.not() }.map { it.id }).containsOnly(seats.`1 1`.id, seats.`2 2`.id, seats.`3 3`.id)
    }

    private fun reservation(vararg seats: Seat) = Reservation("", "", setOf(*seats), screening, setOf())
    private fun reservationRepository(vararg reservation: Reservation) = mock<ReservationRepository> {
        on { findByScreeningId(uuid) }.doReturn(listOf(*reservation))
    }
}