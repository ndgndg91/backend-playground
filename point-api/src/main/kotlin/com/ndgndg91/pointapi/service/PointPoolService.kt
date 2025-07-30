package com.ndgndg91.pointapi.service

import com.ndgndg91.pointapi.entity.PointPoolRepository
import com.ndgndg91.pointapi.service.dto.CreatePointPoolCommand
import org.springframework.stereotype.Service

@Service
class PointPoolService(
    private val repository: PointPoolRepository
) {
    fun creatPointPool(command: CreatePointPoolCommand) {

    }
}