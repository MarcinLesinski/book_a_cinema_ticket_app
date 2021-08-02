package io.stud.forest.book_a_cinema_ticket_app.domain.policy.seats_validation

import io.stud.forest.book_a_cinema_ticket_app.domain.RoomOccupancy
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class NoSingleSeatLeftInRowPolicyTest {

    @Test
    fun `should success when left side seat lefts`() {
        //given
        val policy = NoSingleSeatLeftInRowPolicy(listOf(
                seatNo(1, 1),
                seatNo(1, 2)
        ), RoomOccupancy(listOf(
                freeSeat(1, 1),
                freeSeat(1, 2),
                freeSeat(1, 3),
        )))

        //when
        val check = policy.check()

        //then
        assertThat(check.success).isTrue
    }

    @Test
    fun `should success when right side seat lefts`() {
        //given
        val policy = NoSingleSeatLeftInRowPolicy(listOf(
                seatNo(1, 3)
        ), RoomOccupancy(listOf(
                freeSeat(1, 1),
                takenSeat(1, 2),
                freeSeat(1, 3),
        )
        ))

        //when
        val check = policy.check()

        //then
        assertThat(check.success).isTrue
    }

    @Test
    fun `should success when there is gap of 2 seats`() {
        //given
        val policy = NoSingleSeatLeftInRowPolicy(listOf(
                seatNo(1, 1),
                seatNo(1, 4),
        ), RoomOccupancy(listOf(
                freeSeat(1, 1),
                freeSeat(1, 2),
                freeSeat(1, 3),
                freeSeat(1, 4),
        )
        ))

        //when
        val check = policy.check()

        //then
        assertThat(check.success).isTrue
    }

    @Test
    fun `should fail when there is gap of 1 seats`() {
        //given
        val policy = NoSingleSeatLeftInRowPolicy(listOf(
                seatNo(1, 1),
                seatNo(1, 4),
        ), RoomOccupancy(listOf(
                freeSeat(1, 1),
                freeSeat(1, 2),
                takenSeat(1, 3),
                freeSeat(1, 4),
        )))

        //when
        val check = policy.check()

        //then
        assertThat(check.success).isFalse
    }


}