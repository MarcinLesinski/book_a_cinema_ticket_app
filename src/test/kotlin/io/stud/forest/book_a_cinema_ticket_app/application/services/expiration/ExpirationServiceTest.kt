package io.stud.forest.book_a_cinema_ticket_app.application.services.expiration

import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Reservation
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.Id
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.InMemoryReservationRepository
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.ReservationRepository
import io.stud.forest.book_a_cinema_ticket_app.domain.repositories.findByIdOrNull
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import utils.syntax_sugar.moment
import java.time.LocalDateTime
import java.util.*

internal class ExpirationServiceTest {
    private lateinit var reservationRepository: ReservationRepository

    @BeforeEach
    fun beforeEach() {
        reservationRepository = InMemoryReservationRepository()
    }

    @Test
    fun `should delete not confirmed after given moment`() {
        //given
        val now = moment("2021-11-10 12:16")
        val shouldBeDeleted = mutableListOf<Reservation>()
        reservationRepository.apply {
            shouldBeDeleted.add(save(`not confirmed reservation`(moment("2021-11-10 12:15"))))
            save(`confirmed reservation`(moment("2021-11-10 12:15")))
            shouldBeDeleted.add(save(`not confirmed reservation`(moment("2021-11-09 00:00"))))
            save(`confirmed reservation`(moment("2021-11-11 12:15")))
            save(`not confirmed reservation`(moment("2021-11-10 22:30")))
        }
        val repositorySizeBefore = reservationRepository.findAll().size
        val expirationService = ExpirationService(reservationRepository) { now }

        //when
        expirationService.expireNotConfirmed()

        //then
        val repositorySizeAfter = reservationRepository.findAll().size

        val expiredRemoved = reservationRepository.let {
            (it.findByIdOrNull(shouldBeDeleted[0].id) == null)
                    && (it.findByIdOrNull(shouldBeDeleted[1].id) == null)
        }

        assertThat(repositorySizeAfter).isEqualTo(repositorySizeBefore - 2)
        assertThat(expiredRemoved).isTrue
    }

    @Test
    fun `should delete all after expire time - 15 minutes before screening time`() {
        //given
        val now = moment("2021-11-10 12:16")
        val shouldBeDeleted = mutableListOf<Reservation>()
        reservationRepository.apply {
            shouldBeDeleted.add(save(`reservation expiring`(moment("2021-11-10 12:15"))))
            save(`reservation expiring`(moment("2021-11-10 12:17")))
            save(`reservation expiring`(moment("2021-11-11 12:17")))
            shouldBeDeleted.add(save(`reservation expiring`(moment("2021-11-09 12:17"))))
        }
        val repositorySizeBefore = reservationRepository.findAll().size
        val expirationService = ExpirationService(reservationRepository) { now }

        //when
        expirationService.expireBeforeScreeningTime()

        //then
        val expiredRemoved = reservationRepository.let {
            (it.findByIdOrNull(shouldBeDeleted[0].id) == null)
                    && (it.findByIdOrNull(shouldBeDeleted[1].id) == null)
        }

        val repositorySizeAfter = reservationRepository.findAll().size
        assertThat(repositorySizeAfter).isEqualTo(repositorySizeBefore - 2)
        assertThat(expiredRemoved).isTrue
    }


    private fun `confirmed reservation`(confirmationExpirationMoment: LocalDateTime) =
            Reservation("", "", setOf(), mock(), setOf()).apply {
                this.confirmed = true
                this.confirmationExpireDateTime = confirmationExpirationMoment
            }

    private fun `not confirmed reservation`(confirmationExpirationMoment: LocalDateTime) =
            Reservation("", "", setOf(), mock(), setOf()).apply {
                this.confirmed = false
                this.confirmationExpireDateTime = confirmationExpirationMoment
            }

    private fun `reservation expiring`(expireMoment: LocalDateTime) =
            Reservation("", "", setOf(), mock(), setOf()).apply {
                this.expireTime = expireMoment
                this.confirmed = true
            }
}


