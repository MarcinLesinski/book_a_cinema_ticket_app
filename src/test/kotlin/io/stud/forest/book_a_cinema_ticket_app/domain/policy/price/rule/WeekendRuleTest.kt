package io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.rule


import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import utils.syntax_sugar.*
import utils.syntax_sugar.*

import java.time.LocalDate
import java.time.LocalTime

internal class WeekendRuleTest {

    @ParameterizedTest
    @MethodSource("weekend moments")
    fun `should return true for weekend day`(
            day: LocalDate, time: LocalTime,
    ) {
        //when
        val isMomentOnWeekend = WeekendRule().isWeekend(day, time)

        //then
        assertThat(isMomentOnWeekend).isTrue
    }

    @ParameterizedTest
    @MethodSource("non weekend moments")
    fun `should return false for non weekend day`(
            day: LocalDate, time: LocalTime,
    ) {
        //when
        val isMomentOnWeekend = WeekendRule().isWeekend(day, time)

        //then
        assertThat(isMomentOnWeekend).isFalse
    }

    companion object {
        @JvmStatic
        fun `weekend moments`() = arguments(
                of(friday(), time("14:00:00")),
                of(saturday(), time("00:00:00")),
                of(sunday(), time("00:00:00")),
                of(sunday(), time("22:59:59"))
        )

        @JvmStatic
        fun `non weekend moments`() = arguments(
                of(wednesday(), time("00:00:00")),
                of(friday(), time("13:59:59")),
                of(sunday(), time("23:00:00"))
        )
    }

}