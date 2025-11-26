package com.ndgndg91.project.global.validation.validator

import com.ndgndg91.project.dummy.controller.dto.request.DependenciesBetweenFieldsRequest
import com.ndgndg91.project.global.validation.annotation.ValidDependenciesBetweenFieldsRequest
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class DependenciesBetweenFieldsValidator : ObjectValidationHandler,
    ConstraintValidator<ValidDependenciesBetweenFieldsRequest, DependenciesBetweenFieldsRequest> {
    override fun isValid(req: DependenciesBetweenFieldsRequest, context: ConstraintValidatorContext): Boolean {
        if (req.a == null) {
            return addConstraint(context, "a")
        }

        if (req.dependOnA == null) {
            return addConstraint(context, "dependOnA")
        }

        return when {
            req.a < 0 && req.dependOnA != "NEGATIVE" -> addConstraint(context, "request body")
            req.a > 0 && req.dependOnA != "POSITIVE" -> addConstraint(context, "request body")
            else -> true
        }
    }
}