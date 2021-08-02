package io.stud.forest.book_a_cinema_ticket_app.domain.policy.expire_time

import io.stud.forest.book_a_cinema_ticket_app.domain.entities.Screening
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import utils.syntax_sugar.*

internal class Expire15MinutesBeforeScreeningPolicyTest {

    @Test
    fun `should return moment earlier by 15 minutes`() {
        //given
        val screening = mock(Screening::class.java)
        `when`(screening.day).thenReturn(day("1988-02-11"))
        `when`(screening.startTime).thenReturn(time("00:10:00"))

        //when
        val expireTime = Expire15MinutesBeforeScreeningPolicy().expireTime(screening)

        //then
        assertThat(expireTime).isEqualTo(moment("1988-02-10 23:55:00"))
    }
}