package com.ndgndg91.project.global.validation.validator

import com.ndgndg91.project.global.exception.ErrorCode
import com.ndgndg91.project.global.validation.annotation.ValidEnum
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import kotlin.reflect.KClass

class EnumValidator: ConstraintValidator<ValidEnum, String?> {
    private lateinit var enumValues: List<String>
    private lateinit var enumClass: KClass<out Enum<*>>
    private var nullable: Boolean = false

    override fun initialize(constraintAnnotation: ValidEnum) {
        enumValues = constraintAnnotation.enumClass.java.enumConstants.map { it.name }
        enumClass = constraintAnnotation.enumClass
        nullable = constraintAnnotation.nullable
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        if (nullable && value == null) {
            return true
        }

        if (!nullable && value == null) {
            return addConstraint(context)
        }

        return if (enumValues.contains(value!!.uppercase())) {
            true
        } else {
            addConstraint(context)
        }

    }

    private fun addConstraint(context: ConstraintValidatorContext): Boolean {
        context.disableDefaultConstraintViolation()
        when(enumClass) {
            /**
             * 추가
             */
            else -> {
                context
                    .buildConstraintViolationWithTemplate(ErrorCode.INVALID_PARAMETER.code.toString())
                    .addConstraintViolation()
            }
        }

        return false
    }
}
