package com.ndgndg91.eventpublisher.controller

data class JobResponse(
    val id: String,
    val start: Int,
    val end: Int,
    val result: Long
)