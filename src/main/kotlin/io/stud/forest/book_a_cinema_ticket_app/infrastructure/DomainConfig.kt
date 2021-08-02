package io.stud.forest.book_a_cinema_ticket_app.infrastructure

import io.stud.forest.book_a_cinema_ticket_app.domain.common.TimeProvider
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.expire_time.BookingExpireTimePolicyFactory
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.price.BookingPricePolicyFactory
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.seats_validation.BookingSeatsPolicyFactory
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.time_validation.BookingTimePolicyFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime

@Configuration
class DomainConfig {

//    @Bean
//    fun bookingExpireTimePolicyFactory(): BookingExpireTimePolicyFactory = BookingExpireTimePolicyFactory()
//
//    @Bean
//    fun bookingPricePolicyFactory(timeProvider: TimeProvider): BookingPricePolicyFactory = BookingPricePolicyFactory(timeProvider)
//
//    @Bean
//    fun bookingSeatsPolicyFactory(): BookingSeatsPolicyFactory = BookingSeatsPolicyFactory()
//
//    @Bean
//    fun bookingTimePolicyFactory(): BookingTimePolicyFactory = BookingTimePolicyFactory()

    @Bean
    fun timeProvider(): TimeProvider = { LocalDateTime.now()}
}