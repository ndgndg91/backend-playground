package com.ndgndg91.project.default

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class DefaultEntity(
    @Id
    private var id: Long? = null,
) {
}