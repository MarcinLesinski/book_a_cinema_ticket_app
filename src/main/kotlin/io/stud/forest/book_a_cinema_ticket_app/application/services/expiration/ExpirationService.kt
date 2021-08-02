package io.stud.forest.book_a_cinema_ticket_app.application.services.expiration

import io.stud.forest.book_a_cinema_ticket_app.domain.common.TimeProvider
import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Reservation
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.ReservationRepository
import org.springframework.stereotype.Service
import org.springframework.web.context.annotation.SessionScope

@Service
class ExpirationService(
        private val reservationRepository: ReservationRepository,
        private val timeProvider: TimeProvider,
) {

    fun expireOutdatedReservations() {
        expireBeforeScreeningTime()
        expireNotConfirmed()
    }

    fun expireNotConfirmed() {
        reservationRepository
                .findByConfirmed(false)
                .forEach {
                    if (timeProvider().isAfter(it.confirmationExpireDateTime))
                        reservationRepository.delete(it)
                }
    }

    fun expireBeforeScreeningTime() {
        reservationRepository
                .findByConfirmed(true)
                .filter (::expiredBasedOnDataWhenReservationWasMade)
                .forEach(reservationRepository::delete)
    }

    fun expiredBasedOnDataWhenReservationWasMade(reservation: Reservation): Boolean {
        return  timeProvider().isAfter(reservation.expireTime)
    }

    fun expiredBasedOnActualData(reservation: Reservation): Boolean {
        val `15 minutes before screening` = reservation.screening.dateTime().minusMinutes(15)
        return  timeProvider().isAfter(`15 minutes before screening`)
    }
}