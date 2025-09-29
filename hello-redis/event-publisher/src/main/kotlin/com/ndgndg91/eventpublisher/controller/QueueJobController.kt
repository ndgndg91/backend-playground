package com.ndgndg91.eventpublisher.controller

import com.ndgndg91.eventpublisher.service.QueueService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class QueueJobController(
    val service: QueueService,
) {

    @PostMapping("/apis/jobs")
    fun generate(@RequestBody body: QueueJobRequest): ResponseEntity<Unit> {
        val jobId = service.queue(body.start, body.end)
        return ResponseEntity.created(URI.create("/apis/jobs/${jobId}")).build()
    }

    @GetMapping("/apis/jobs/{id}")
    fun generate(@PathVariable id: String): ResponseEntity<JobResponse> {
        val result = service.findJob(id) ?: throw RuntimeException("No job found for $id")
        return ResponseEntity.ok(
            JobResponse(
                id = result.job.id,
                start = result.job.start,
                end = result.job.end,
                result = result.result
            )
        )
    }
}