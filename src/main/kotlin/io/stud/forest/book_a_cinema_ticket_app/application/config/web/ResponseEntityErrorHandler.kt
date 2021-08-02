package io.stud.forest.book_a_cinema_ticket_app.application.config.web

import io.stud.forest.book_a_cinema_ticket_app.domain.policy.seats_validation.BookingSeatsPolicyException
import io.stud.forest.book_a_cinema_ticket_app.domain.policy.time_validation.BookingTimePolicyException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*
import java.util.stream.Collectors

@ControllerAdvice
class ResponseEntityErrorHandler : ResponseEntityExceptionHandler() {

    /**
     *  handler for validation [@Valid] errors
     */
    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = Date()
        body["status"] = status.value()

        val errors = ex.bindingResult
                .fieldErrors
                .stream()
                .map { x: FieldError -> x.defaultMessage }
                .collect(Collectors.toList())
        body["errors"] = errors
        return ResponseEntity(body, headers, status)
    }


    @ExceptionHandler(BookingTimePolicyException::class)
    fun handle(ex: BookingTimePolicyException): ResponseEntity<Any>{
        val apiError = ApiError(HttpStatus.BAD_REQUEST)
        apiError.addError(ex.message)
        return buildResponseEntity(apiError)
    }

    @ExceptionHandler(BookingSeatsPolicyException::class)
    fun handle(ex: BookingSeatsPolicyException): ResponseEntity<Any>{
        val apiError = ApiError(HttpStatus.BAD_REQUEST)
        apiError.addError(ex.message)
        return buildResponseEntity(apiError)
    }

    private fun buildResponseEntity(apiError: ApiError): ResponseEntity<Any> {
        return ResponseEntity(apiError, apiError.status)
    }
}