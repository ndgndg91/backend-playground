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
class SecondaryDataSourceConfig {
    companion object {
        const val SECONDARY_HIKARI: String = "SECONDARY_HIKARI"
        const val SECONDARY_DATASOURCE: String = "SECONDARY_DATASOURCE"
        const val SECONDARY_DATASOURCE_PROPERTY: String = "infra.secondary-datasource"
        const val SECONDARY_TRANSACTION_MANAGER: String = "SECONDARY_TRANSACTION_MANAGER"
    }

    @ConfigurationProperties(prefix = SECONDARY_DATASOURCE_PROPERTY)
    data class SecondaryDataSourceProperty(
        val jdbcUrl: String,
        val username: String,
        val password: String,
        val driverClassName: String
    )

    @Bean(name = [SECONDARY_HIKARI])
    fun secondaryHikari(
        property: SecondaryDataSourceProperty
    ): HikariConfig {
        return HikariConfig().apply {
            jdbcUrl = property.jdbcUrl
            username = property.username
            password = property.password
            driverClassName = property.driverClassName
            poolName = SECONDARY_DATASOURCE
        }
    }

    @Bean(name = [SECONDARY_DATASOURCE])
    fun secondaryDataSource(
        @Qualifier(SECONDARY_HIKARI) secondaryHikari: HikariConfig
    ): DataSource {
        return HikariDataSource(secondaryHikari)
    }

    @Bean(name = [SECONDARY_TRANSACTION_MANAGER])
    fun secondaryTransactionManager(
        @Qualifier(SECONDARY_DATASOURCE) secondaryDataSource: DataSource
    ): PlatformTransactionManager {
        return DataSourceTransactionManager(secondaryDataSource)
    }
}