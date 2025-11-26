package com.ndgndg91.project.global.config.datasource

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
class DefaultDataSourceConfig {

    companion object {
        const val DEFAULT_HIKARI: String = "DEFAULT_HIKARI"
        const val DEFAULT_DATASOURCE: String = "DEFAULT_DATASOURCE"
        const val DEFAULT_TRANSACTION_MANAGER: String = "DEFAULT_TRANSACTION_MANAGER"
        private const val DEFAULT_DATASOURCE_PROPERTY: String = "infra.default-datasource"
    }

    @ConfigurationProperties(prefix = DEFAULT_DATASOURCE_PROPERTY)
    data class DefaultDataSourceProperty(
        val jdbcUrl: String,
        val username: String,
        val password: String,
        val driverClassName: String,
        val connectionTimeOut: Long,
        val idleTimeout: Long,
        val maximumPoolSize: Int,
        val minimumIdle: Int,
        val maxLifetime: Long
    )

    @Bean(name = [DEFAULT_HIKARI])
    fun defaultHikari(
        property: DefaultDataSourceProperty
    ): HikariConfig {
        return HikariConfig().apply {
            jdbcUrl = property.jdbcUrl
            username = property.username
            password = property.password
            driverClassName = property.driverClassName
            connectionTimeout = property.connectionTimeOut
            maximumPoolSize = property.maximumPoolSize
            minimumIdle = property.minimumIdle
            idleTimeout = property.idleTimeout
            maxLifetime = property.maxLifetime
            poolName = DEFAULT_DATASOURCE
        }
    }


    @Bean(name = [DEFAULT_DATASOURCE])
    fun defaultDataSource(
        @Qualifier(DEFAULT_HIKARI) defaultHikari: HikariConfig
    ): DataSource {
        return HikariDataSource(defaultHikari)
    }

    @Bean(name = [DEFAULT_TRANSACTION_MANAGER])
    fun defaultTransactionManager(
        @Qualifier(DEFAULT_DATASOURCE) defaultDataSource: DataSource
    ): PlatformTransactionManager {
        return DataSourceTransactionManager(defaultDataSource)
    }
}