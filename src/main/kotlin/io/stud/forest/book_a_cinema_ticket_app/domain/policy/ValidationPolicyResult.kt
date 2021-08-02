package io.stud.forest.book_a_cinema_ticket_app.domain.policy

class ValidationPolicyResult(
        val success: Boolean,
        val errors: List<String>
){
    companion object{
        fun success(): ValidationPolicyResult = ValidationPolicyResult(true, listOf())
        fun error(vararg messages: String) = ValidationPolicyResult(false, listOf(*messages))
    }

}