package com.ndgndg91.project.secondary

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class SecondaryEntity(
    @Id
    private var id: Long? = null,
) {
}