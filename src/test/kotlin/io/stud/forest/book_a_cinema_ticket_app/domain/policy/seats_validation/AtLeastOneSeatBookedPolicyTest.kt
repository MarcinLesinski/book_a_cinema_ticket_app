package io.stud.forest.book_a_cinema_ticket_app.domain.policy.seats_validation

import io.stud.forest.book_a_cinema_ticket_app.domain.SeatNumber
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class AtLeastOneSeatBookedPolicyTest {

    @Test
    fun `should fail for empty list of seats`() {
        //given
        val atLeastOneSeatBookedPolicy = AtLeastOneSeatBookedPolicy(listOf())

        //when
        val check =  atLeastOneSeatBookedPolicy.check()

        //then
        assertThat(check.success).isFalse
    }

    @ParameterizedTest
    @ValueSource(longs = [1, 5, 11])
    fun `should success for not empty list of seats`(numberOfCharis: Long) {
        //given
        val seats = List(numberOfCharis.toInt()){ SeatNumber(0, 0) }
        val atLeastOneSeatBookedPolicy = AtLeastOneSeatBookedPolicy(seats)

        //when
        val check =  atLeastOneSeatBookedPolicy.check()

        //then
        assertThat(check.success).isTrue
    }


}