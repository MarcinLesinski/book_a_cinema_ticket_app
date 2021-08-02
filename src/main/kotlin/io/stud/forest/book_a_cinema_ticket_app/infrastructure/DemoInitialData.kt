package io.stud.forest.book_a_cinema_ticket_app.infrastructure

import io.stud.forest.book_a_cinema_ticket_app.domain.entities.*
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.*
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.MovieRepository
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.RoomRepository
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.ScreeningRepository
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.TicketTypeRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime

@Component()
@ConditionalOnProperty(value = ["properties.demo.initial-data"], havingValue = "true", matchIfMissing = false)
class DemoInitialData(
        moviesRepository: MovieRepository,
        roomRepository: RoomRepository,
        ticketTypeRepository: TicketTypeRepository,
        screeningRepository: ScreeningRepository,
        voucherCodeRepository: VoucherCodeRepository
) : ApplicationRunner {

    private val movies = Movies(moviesRepository)
    private val rooms = Rooms(roomRepository)
    private val ticketTypes = TicketTypes(ticketTypeRepository)
    private val screenings = Screenings(screeningRepository, movies, rooms)
    private val vouchers = Vouchers(voucherCodeRepository)

    override fun run(args: ApplicationArguments?) {
        movies.init()
        rooms.init()
        ticketTypes.init()
        screenings.init()
        vouchers.init()
    }
}

class Movies(
        private val moviesRepository: MovieRepository,
) {
    lateinit var spiderMan: Movie
    lateinit var lastSamurai: Movie
    lateinit var angryMen: Movie
    lateinit var intouchables: Movie
    lateinit var joker: Movie

    fun init() {
        angryMen = Movie().apply {
            this.title = "12 Angry Men"
            this.description = "Life is in their hands - Death is on their minds!"
            this.author = "Sidney Lumet"
            this.durationInMinutes = 96
            this.legalAge = 12
        }.run(moviesRepository::save)

        lastSamurai = Movie().apply {
            this.title = "The Last Samurai"
            this.description = "Set in Japan during the 1870s, The Last Samurai tells the story of Capt. Nathan Algren (Tom Cruise), an American military officer hired by the Emperor of Japan to train the country's first army in the art of modern warfare."
            this.author = "Edward Zwick"
            this.durationInMinutes = 154
            this.legalAge = 12
        }.run(moviesRepository::save)

        joker = Movie().apply {
            this.title = "Joker"
            this.description = "The comedian, weary of life, goes mad and becomes a psychopathic murderer."
            this.author = "Todd Phillips"
            this.durationInMinutes = 122
            this.legalAge = 15
        }.run(moviesRepository::save)

        spiderMan = Movie().apply {
            this.title = "Spider-Man - into the spider-verse"
            this.description = "Teen Miles Morales becomes the Spider-Man of his universe, and must join with five spider-powered individuals from other dimensions to stop a threat for all realities."
            this.author = "Bob Persichetti, Peter Ramsey, Rodney Rothman"
            this.durationInMinutes = 117
            this.legalAge = 7
        }.run(moviesRepository::save)

        intouchables = Movie().apply {
            this.title = "The Intouchables"
            this.description = "A paralyzed millionaire hires a young suburban boy who has just come out of prison to look after him."
            this.author = "Olivier Nakache, Éric Toledano"
            this.durationInMinutes = 112
            this.legalAge = 9
        }.run(moviesRepository::save)
    }
}

class Rooms(
        private val roomRepository: RoomRepository,
) {
    lateinit var a: Room
    lateinit var b: Room
    lateinit var c: Room
    lateinit var vip: Room

    fun init() {
        a = Room("A").apply {
            this.addSeats(createSeats(10, 10))
        }.run(roomRepository::save)

        b = Room("B").apply {
            this.addSeats(createSeats(8, 8))
        }.run(roomRepository::save)

        c = Room("C").apply {
            this.addSeats(createSeats(5, 5))
        }.run(roomRepository::save)

        vip = Room("VIP").apply {
            this.addSeats(createSeatsWithGivenRowSizes(3, 5, 7, 9, 1, 1, 1, 1))
        }.run(roomRepository::save)
    }

    //candidate for Room Design module
    private fun createSeats(rows: Int, seatsCountInRow: Int): List<Seat> {
        return List(rows) { row ->
            List(seatsCountInRow) { number ->
                Seat(row.toLong() + 1, number.toLong() + 1, row == 1)
            }
        }.flatten()
    }

    //candidate for Room Design module
    private fun createSeatsWithGivenRowSizes(vararg numbersOfSeatInRow: Long): List<Seat> {
        return numbersOfSeatInRow.mapIndexed { rowNumber, rowSize ->
            List(rowSize.toInt()) {
                Seat(rowNumber.toLong() + 1, it.toLong() + 1, true)
            }
        }.flatten()
    }
}

class TicketTypes(
        private val ticketTypeRepository: TicketTypeRepository,
) {
    fun init() {
        listOf(
                TicketType("adult", BigDecimal("25"), "default ticket"),
                TicketType("student", BigDecimal("18"), "requires a valid student card"),
                TicketType("child", BigDecimal("12.50"), "requires a valid school card")
        ).run(ticketTypeRepository::saveAll)
    }
}

class Screenings(
        private val screeningRepository: ScreeningRepository,
        private val movies: Movies,
        private val rooms: Rooms,
) {
    fun init() {
        val normalDay = LocalDate.of(2021, 9, 30)
        listOf(
                Screening(movies.spiderMan, rooms.a, normalDay, LocalTime.of(10, 0)),
                Screening(movies.intouchables, rooms.a, normalDay, LocalTime.of(15, 30)),
                Screening(movies.lastSamurai, rooms.a, normalDay, LocalTime.of(21, 0)),

                Screening(movies.spiderMan, rooms.b, normalDay, LocalTime.of(11, 30)),
                Screening(movies.joker, rooms.b, normalDay, LocalTime.of(17, 0)),
                Screening(movies.intouchables, rooms.b, normalDay, LocalTime.of(19, 30)),

                Screening(movies.spiderMan, rooms.c, normalDay, LocalTime.of(7, 30)),
                Screening(movies.lastSamurai, rooms.c, normalDay, LocalTime.of(15, 30)),
                Screening(movies.angryMen, rooms.c, normalDay, LocalTime.of(19, 30)),

                Screening(movies.spiderMan, rooms.vip, normalDay, LocalTime.of(17, 0)),
                Screening(movies.angryMen, rooms.vip, normalDay, LocalTime.of(22, 0)),

                ).run(screeningRepository::saveAll)

        val weekendDay = LocalDate.of(2021, 9, 25)

        listOf(
                Screening(movies.spiderMan, rooms.a, weekendDay, LocalTime.of(10, 0)),
                Screening(movies.intouchables, rooms.a, weekendDay, LocalTime.of(15, 30)),
                Screening(movies.lastSamurai, rooms.a, weekendDay, LocalTime.of(21, 0)),

                Screening(movies.spiderMan, rooms.b, weekendDay, LocalTime.of(11, 30)),
                Screening(movies.joker, rooms.b, weekendDay, LocalTime.of(17, 0)),
                Screening(movies.intouchables, rooms.b, weekendDay, LocalTime.of(19, 30)),

                Screening(movies.spiderMan, rooms.c, weekendDay, LocalTime.of(7, 30)),
                Screening(movies.lastSamurai, rooms.c, weekendDay, LocalTime.of(15, 30)),
                Screening(movies.angryMen, rooms.c, weekendDay, LocalTime.of(19, 30)),

                Screening(movies.spiderMan, rooms.vip, weekendDay, LocalTime.of(17, 0)),
                Screening(movies.angryMen, rooms.vip, weekendDay, LocalTime.of(22, 0)),

                ).run(screeningRepository::saveAll)

    }
}

class Vouchers(
        private val voucherCodeRepository: VoucherCodeRepository
){
    fun init(){
        val voucher50PercentInfiniteUsage = VoucherCode.percentageDiscount("#PROMO_CODE", BigDecimal.valueOf(50))

        voucherCodeRepository.save(voucher50PercentInfiniteUsage)
    }
}