package io.stud.forest.book_a_cinema_ticket_app.application.config.web

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpStatus
import java.time.LocalDateTime


class ApiError(var status: HttpStatus) {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    val timestamp: LocalDateTime = LocalDateTime.now()
    val errors: MutableList<String> = mutableListOf()

    fun addError(vararg errors: String?){
        errors.map{ it ?: "Unexpected error" }.run(this.errors::addAll)
    }
}

