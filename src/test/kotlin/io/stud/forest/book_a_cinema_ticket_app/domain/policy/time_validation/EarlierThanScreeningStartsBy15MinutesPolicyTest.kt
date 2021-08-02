package io.stud.forest.book_a_cinema_ticket_app.domain.policy.time_validation


import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Screening
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import utils.syntax_sugar.*
import java.time.LocalDateTime

internal class EarlierThanScreeningStartsBy15MinutesPolicyTest {

    private lateinit var screening: Screening


    @BeforeEach
    fun beforeEach() {
        screening = mock(Screening::class.java)
    }

    @ParameterizedTest
    @MethodSource("moment | more than 15 minutes earlier")
    fun `should success for moment of time 15 minutes before screening start`(
            screeningStart: LocalDateTime,
            momentOfTime: LocalDateTime,
    ) {
        //given
        `when`(screening.day).thenReturn(screeningStart.toLocalDate())
        `when`(screening.startTime).thenReturn(screeningStart.toLocalTime())
        val policy = EarlierThanScreeningStartsBy15MinutesPolicy(screening) { momentOfTime }

        //when
        val actual = policy.check()

        //then
        assertThat(actual.success).isTrue
    }

    @ParameterizedTest
    @MethodSource("moment | equal or less than 15 minutes earlier")
    fun `should fail for moment of time after or equal 15 minutes before screening start`(
            screeningStart: LocalDateTime,
            momentOfTime: LocalDateTime,
    ) {
        //given
        `when`(screening.day).thenReturn(screeningStart.toLocalDate())
        `when`(screening.startTime).thenReturn(screeningStart.toLocalTime())
        val policy = EarlierThanScreeningStartsBy15MinutesPolicy(screening) { momentOfTime }

        //when
        val actual = policy.check()

        //then
        assertThat(actual.success).isFalse
    }

    companion object {

        @JvmStatic
        fun `moment | more than 15 minutes earlier`() = arguments(
                of(
                        moment("2021-11-30 14:30:00"),
                        moment("2021-11-30 14:14:59")),
                of(
                        moment("2021-11-30 14:30:00"),
                        moment("2021-11-30 14:14:00")),
                of(
                        moment("2021-11-30 14:30:00"),
                        moment("2021-11-30 13:30:00")),
                of(
                        moment("2021-11-30 14:30:00"),
                        moment("2020-11-30 14:30:00"))
        )

        @JvmStatic
        fun `moment | equal or less than 15 minutes earlier`() = arguments(
                of(
                        moment("2021-11-30 14:30:00"),
                        moment("2021-11-30 14:30:00")),
                of(
                        moment("2021-11-30 14:30:00"),
                        moment("2021-11-30 14:15:00")),
                of(
                        moment("2021-11-30 14:30:00"),
                        moment("2021-11-30 14:15:01")),
                of(
                        moment("2021-11-30 14:30:00"),
                        moment("2022-11-30 14:14:59")),
        )

    }
}
