package com.ndgndg91.project.global.config.documentdb

import org.bson.UuidRepresentation
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "infra.document-db")
data class MongoDBProperty(
    val uri: String,
    val database: String,
    val username: String,
    val password: String,
    val uuidRepresentation: UuidRepresentation = UuidRepresentation.UNSPECIFIED,
    val ssl: SSLProperty = SSLProperty(),
    val pool: PoolProperty = PoolProperty(),
    val connection: ConnectionProperty = ConnectionProperty(),
) {
    data class SSLProperty(
        val isEnabled: Boolean = false,
        val bundle: String = "global-bundle.pem",
    )

    data class PoolProperty(
        val maxSize: Int = 50,
        val minSize: Int = 10,
        val maxConnectionLifeMin: Long = 1L,
    )

    data class ConnectionProperty(
        val timeoutSec: Long = 30,
        val readTimeoutSec: Long = 30,
    )
}