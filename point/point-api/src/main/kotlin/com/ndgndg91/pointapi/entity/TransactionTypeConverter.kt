package com.ndgndg91.pointapi.entity

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class TransactionTypeConverter: AttributeConverter<TransactionType, Int> {
    override fun convertToDatabaseColumn(status: TransactionType): Int {
        return status.code
    }

    override fun convertToEntityAttribute(dbValue: Int): TransactionType {
        return TransactionType.fromDbValue(dbValue)
    }
}