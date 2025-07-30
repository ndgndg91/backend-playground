package com.ndgndg91.pointapi.entity

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

/**
 * PointPoolStatus Enum과 DB의 Integer 값을 변환하는 컨버터
 */
@Converter(autoApply = true)
class PointPoolStatusConverter: AttributeConverter<PointPoolStatus, Int> {

    override fun convertToDatabaseColumn(attribute: PointPoolStatus?): Int? {
        return attribute?.code
    }

    override fun convertToEntityAttribute(dbData: Int?): PointPoolStatus? {
        if (dbData == null) {
            return null
        }
        return PointPoolStatus.fromCode(dbData)
    }
}