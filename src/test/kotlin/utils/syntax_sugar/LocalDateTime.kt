package utils.syntax_sugar

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * LocalDateTime sugar sytnax
 *
 * HINT: To consider:  use it in domain
 *
 */
fun day(date: String): LocalDate = LocalDate.parse(date)
fun moment(date: String, time: String): LocalDateTime = moment("${date}T$time")
fun moment(dateTime: String): LocalDateTime = LocalDateTime.parse(dateTime.replace(' ', 'T'))
fun time(time: String): LocalTime = LocalTime.parse(time)

fun monday(): LocalDate = LocalDate.parse("2021-09-20")
fun tuesday(): LocalDate = LocalDate.parse("2021-09-21")
fun wednesday(): LocalDate = LocalDate.parse("2021-09-22")
fun thursday(): LocalDate = LocalDate.parse("2021-09-23")
fun friday(): LocalDate = LocalDate.parse("2021-09-24")
fun saturday(): LocalDate = LocalDate.parse("2021-09-25")
fun sunday(): LocalDate = LocalDate.parse("2021-09-26")

