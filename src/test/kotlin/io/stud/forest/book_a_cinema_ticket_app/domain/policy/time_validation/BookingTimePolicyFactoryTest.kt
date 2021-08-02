package io.stud.forest.book_a_cinema_ticket_app.domain.policy.time_validation

import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Screening
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.mock
import java.time.LocalDateTime

internal class BookingTimePolicyFactoryTest {
    private lateinit var screening: Screening
    private val factory = BookingTimePolicyFactory()

    @BeforeEach
    fun beforeEach(){
        screening = mock(Screening::class.java)
    }

    @Test
    fun `should create BookingTimePolicyFactory`(){
        //given
            screening
            val timeProvider = {LocalDateTime.MIN}

        //when
            val actual = factory.create(screening, timeProvider)

        //then
        assertThat(actual).isInstanceOf(EarlierThanScreeningStartsBy15MinutesPolicy::class.java)
    }
}