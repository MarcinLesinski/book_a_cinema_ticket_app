package io.stud.forest.book_a_cinema_ticket_app.domain.policy.seats_validation

import io.stud.forest.book_a_cinema_ticket_app.domain.RoomOccupancy
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class CanNotBookAlreadyBookedSeatPolicyTest {

    private val room2ndRowFree = RoomOccupancy(
            listOf(
                    takenSeat(1, 1),
                    takenSeat(1, 2),
                    takenSeat(1, 3),
                    takenSeat(1, 4),
                    takenSeat(1, 5),
                    freeSeat(2, 1),
                    freeSeat(2, 2),
                    freeSeat(2, 3),
                    freeSeat(2, 4),
                    freeSeat(2, 5),
            )
    )

    @Test
    fun `should success when book not taken seats`() {
        //given
        val policy = CanNotBookAlreadyBookedSeatPolicy(
                listOf(
                        seatNo(2, 1),
                        seatNo(2, 2),
                        seatNo(2, 5)
                ),
                room2ndRowFree)

        //when
        val check = policy.check()

        //then
        assertThat(check.success).isTrue
    }

    @Test
    fun `should success when book no seats`() {
        //given
        val policy = CanNotBookAlreadyBookedSeatPolicy(
                listOf(
                    /*no seats*/
                ),
                room2ndRowFree)

        //when
        val check = policy.check()

        //then
        assertThat(check.success).isTrue
    }

    @Test
    fun `should fail when book some taken seats`() {
        //given
        val policy = CanNotBookAlreadyBookedSeatPolicy(
                listOf(
                        seatNo(2, 1),
                        seatNo(2, 2),
                        seatNo(1, 5)
                ),
                room2ndRowFree)

        //when
        val check = policy.check()

        //then
        assertThat(check.success).isFalse
    }

    @Test
    fun `should success when book not existing seats`() {
        //given
        val policy = CanNotBookAlreadyBookedSeatPolicy(
                listOf(
                        seatNo(999, 999),
                        seatNo(0, 4),
                        seatNo(3, 0),
                        seatNo(0, 0)
                ),
                room2ndRowFree)

        //when
        val check = policy.check()

        //then
        assertThat(check.success).isTrue
    }
}