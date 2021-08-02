package io.stud.forest.book_a_cinema_ticket_app.utils

import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.constraints.Pattern
import kotlin.reflect.KClass

@Pattern(regexp = "^[A-ZŻŹĆĄŚĘŁÓŃ][a-zżźćńółęąś]{2,}$")
@kotlin.annotation.Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.ANNOTATION_CLASS)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@kotlin.annotation.Repeatable
@MustBeDocumented
@Constraint(validatedBy = [])
annotation class VV(
        val message: String = "at least three characters long, starting with a capital letter",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Any>> = [],
)

@Pattern(regexp = "^[A-ZŻŹĆĄŚĘŁÓŃ][a-zżźćńółęąś]{2,}(-[A-ZŻŹĆĄŚĘŁÓŃ][a-zżźćńółęąś]{2,})?$")
@kotlin.annotation.Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.ANNOTATION_CLASS)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@kotlin.annotation.Repeatable
@MustBeDocumented
@Constraint(validatedBy = [])
annotation class ValidSurName(
        val message: String = "at least three characters long, starting with a capital letter",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Any>> = [],
)

@Target(
        AnnotationTarget.FIELD,
        AnnotationTarget.ANNOTATION_CLASS,
        AnnotationTarget.VALUE_PARAMETER
)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [ValidNameValidator::class])
annotation class ValidName(

        val message: String = "A valid name should be at least three characters long, starting with a capital letter",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Any>> = [],
)

class ValidNameValidator : ConstraintValidator<ValidName, String> {

    companion object {
        val pattern = "^[A-ZŻŹĆĄŚĘŁÓŃ][a-zżźćńółęąś]{2,}$".toRegex()
    }

    override fun isValid(value: String, context: ConstraintValidatorContext?) = pattern.matches(value)
}


@Target(
        AnnotationTarget.FIELD,
        AnnotationTarget.ANNOTATION_CLASS,
        AnnotationTarget.VALUE_PARAMETER
)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [ValidSurnameValidator::class])
annotation class ValidSurname(

        val message: String = "A valid surname should be at least three characters long, starting with a capital letter, " +
                "can consist of two parts separated with a single dash, in this case the second part should also start with a capital letter.",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Any>> = [],
)

class ValidSurnameValidator : ConstraintValidator<ValidSurname, String> {

    companion object {
        val pattern = "^[A-ZŻŹĆĄŚĘŁÓŃ][a-zżźćńółęąś]{2,}(-[A-ZŻŹĆĄŚĘŁÓŃ][a-zżźćńółęąś]{2,})?$".toRegex()
    }

    override fun isValid(value: String, context: ConstraintValidatorContext?) = pattern.matches(value)
}

