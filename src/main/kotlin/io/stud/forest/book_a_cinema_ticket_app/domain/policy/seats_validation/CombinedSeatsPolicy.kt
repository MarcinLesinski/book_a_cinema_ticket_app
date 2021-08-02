package io.stud.forest.book_a_cinema_ticket_app.domain.policy.seats_validation

import io.stud.forest.book_a_cinema_ticket_app.domain.policy.ValidationPolicyResult

class CombinedSeatsPolicy(
        vararg policies: BookingSeatsPolicy,
        private val logic: (Boolean, Boolean) -> Boolean,
) : BookingSeatsPolicy {
    private val policies = policies.asList()

    override fun check(): ValidationPolicyResult {

        val policyResults = policies.map { it.check()}
        val isValid = policyResults.map{it.success}.reduce(logic)
        val errors = policyResults.map{it.errors}.flatten()

        return if (isValid)
            ValidationPolicyResult.success()
        else
            ValidationPolicyResult.error(*errors.toTypedArray())
    }
}