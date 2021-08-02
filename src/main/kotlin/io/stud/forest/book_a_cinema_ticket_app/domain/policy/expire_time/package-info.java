/**
 * For currently existing rules for calculating expiration time,
 * it can be calculated when Entity is created(Expiration time is const for particular movie).
 *
 * However, I leave this implementation in case let's say:
 * - the premium user booking will not expired at all
 * - expiration time will depends on room's row
 * - expiration time will depends on actual amount of reservation for screening
 */
package io.stud.forest.book_a_cinema_ticket_app.domain.policy.expire_time;