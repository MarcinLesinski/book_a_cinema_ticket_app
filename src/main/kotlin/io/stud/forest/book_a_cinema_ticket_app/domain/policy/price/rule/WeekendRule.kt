package io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.rule

import org.springframework.stereotype.Component
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Component
class WeekendRule {
    fun isWeekend(date: LocalDate, time: LocalTime): Boolean = isWeekend(LocalDateTime.of(date, time))
    private fun isWeekend(dateTime: LocalDateTime): Boolean {
        val fridayAfter2PM = { (dateTime.dayOfWeek == DayOfWeek.FRIDAY) && (dateTime.hour >= 14) }
        val saturday = { (dateTime.dayOfWeek == DayOfWeek.SATURDAY) }
        val sundayBefore11PM = { (dateTime.dayOfWeek == DayOfWeek.SUNDAY) && (dateTime.hour < 23) }
        return saturday() || sundayBefore11PM() || fridayAfter2PM()
    }
}