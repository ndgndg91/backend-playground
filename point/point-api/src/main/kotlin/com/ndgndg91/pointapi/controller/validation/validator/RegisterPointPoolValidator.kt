package com.ndgndg91.pointapi.controller.validation.validator

import com.ndgndg91.pointapi.controller.dto.request.RegisterPointPoolRequest
import com.ndgndg91.pointapi.controller.validation.annotation.ValidRegisterPointPoolRequest
import com.ndgndg91.pointapi.global.ErrorCode
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class RegisterPointPoolValidator: ConstraintValidator<ValidRegisterPointPoolRequest, RegisterPointPoolRequest> {
    override fun isValid(body: RegisterPointPoolRequest?, context: ConstraintValidatorContext): Boolean {
        if (body == null) return addConstraint(context, ErrorCode.INVALID_PARAM.code)

        return true
    }

    private fun addConstraint(context: ConstraintValidatorContext, errorCode: Int): Boolean {
        context.disableDefaultConstraintViolation()
        context.buildConstraintViolationWithTemplate(errorCode.toString()).addConstraintViolation()
        return false
    }
}