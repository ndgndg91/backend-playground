package com.ndgndg91.project.global.validation.validator

import com.ndgndg91.project.global.exception.ErrorCode
import jakarta.validation.ConstraintValidatorContext

interface ObjectValidationHandler {
    fun addConstraint(context: ConstraintValidatorContext, fieldName: String, errorCode: ErrorCode = ErrorCode.INVALID_PARAMETER): Boolean {
        context.disableDefaultConstraintViolation()
        context
            .buildConstraintViolationWithTemplate(errorCode.code.toString())
            .addPropertyNode(fieldName)
            .addConstraintViolation()
        return false
    }
}