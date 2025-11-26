package com.ndgndg91.project.dummy.controller.dto.request

import com.ndgndg91.project.adaptor.domain.DummyEnum
import com.ndgndg91.project.dummy.service.dto.command.CreateDummyCommand
import com.ndgndg91.project.global.validation.annotation.ValidEnum
import com.ndgndg91.project.global.validation.annotation.ValidEnumSet


data class CreateDummyRequest(
    @field:ValidEnum(enumClass = DummyEnum::class) val dummyType: String?,
    @field:ValidEnumSet(enumClass = DummyEnum::class) val dummyTypes: List<String>
) {
    fun toCommand(): CreateDummyCommand {
        return CreateDummyCommand(
            type = DummyEnum.valueOf(dummyType!!),
            types = dummyTypes.map { DummyEnum.valueOf(it) }
        )
    }
}