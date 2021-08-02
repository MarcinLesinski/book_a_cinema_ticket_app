package io.stud.forest.book_a_cinema_ticket_app.application.services

import io.stud.forest.book_a_cinema_ticket_app.application.dto.ReservationReadDto
import io.stud.forest.book_a_cinema_ticket_app.application.dto.ReservationWriteDto
import io.stud.forest.book_a_cinema_ticket_app.application.services.booking.ReservationExpiredException
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.expire_time.BookingExpireTimePolicyFactory
import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Reservation
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.BookingPricePolicyFactory
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.seats_validation.BookingSeatsPolicyException
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.seats_validation.BookingSeatsPolicyFactory
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.time_validation.BookingTimePolicyException
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.time_validation.BookingTimePolicyFactory
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.*
import org.springframework.stereotype.Service
import org.springframework.web.context.annotation.RequestScope
import org.springframework.web.context.annotation.SessionScope
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
@Transactional
class BookingService(
        private val roomService: RoomsService,
        private val seatRepository: SeatRepository,
        private val ticketTypeRepository: TicketTypeRepository,
        private val screeningRepository: ScreeningRepository,
        private val reservationRepository: ReservationRepository,
        private val bookingTimePolicyFactory: BookingTimePolicyFactory,
        private val bookingSeatsPolicyFactory: BookingSeatsPolicyFactory,
        private val bookingPricePolicyFactory: BookingPricePolicyFactory,
        private val bookingExpireTimePolicyFactory: BookingExpireTimePolicyFactory,
) {
    fun book(reservationDto: ReservationWriteDto): ReservationReadDto {

        val screening = screeningRepository.findByIdOrNull(reservationDto.screeningId) ?: error("screening not found")
        val roomOccupancy = roomService.getOccupancy(screening)
        val seats = seatRepository.findAllById(reservationDto.seats)
        val bookingTimePolicy = bookingTimePolicyFactory.create(screening) { LocalDateTime.now() }
        val bookingSeatsPolicy = bookingSeatsPolicyFactory.create(seats.map{it.number()}, roomOccupancy)

        val bookingTimeValidation = bookingTimePolicy.check()
        val bookingSeatsValidation = bookingSeatsPolicy.check()
        if (bookingTimeValidation.success.not()) throw BookingTimePolicyException(bookingTimeValidation.errors.toString())
        if (bookingSeatsValidation.success.not()) throw BookingSeatsPolicyException(bookingSeatsValidation.errors.toString())

        val bookedTicketsTypes = ticketTypeRepository.findAllById(reservationDto.tickets)

        val totalAmountToPay = bookingPricePolicyFactory.create(screening, bookedTicketsTypes, reservationDto.voucherCode).totalAmountToPay()
        val expireTime = bookingExpireTimePolicyFactory.create().expireTime(screening)

        val persistedReservation = Reservation(
                reservationDto.bookerName,
                reservationDto.bookerSurname,
                seats.toSet(),
                screening,
                bookedTicketsTypes.toSet()
        ).apply {
            this.totalAmountToPay = totalAmountToPay
            this.expireTime = expireTime
        }.let(reservationRepository::save)

        return ReservationReadDto.of(persistedReservation)
    }

    fun confirm(reservationId: Id){
        val reservation = reservationRepository.findByIdOrNull(reservationId)
        reservation?.let{
            it.confirmed = true
        } ?: throw ReservationExpiredException()
    }
}