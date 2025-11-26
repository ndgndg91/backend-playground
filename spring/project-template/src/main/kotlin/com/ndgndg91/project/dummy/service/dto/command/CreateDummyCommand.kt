package com.ndgndg91.project.dummy.service.dto.command

import com.ndgndg91.project.adaptor.domain.DummyEnum


data class CreateDummyCommand(
    val type: DummyEnum,
    val types: List<DummyEnum>
)