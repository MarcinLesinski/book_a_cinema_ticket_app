package io.stud.forest.book_a_cinema_ticket_app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class BookACinemaTicketAppApplication

fun main(args: Array<String>) {
	runApplication<BookACinemaTicketAppApplication>(*args)
}