package com.ndgndg91.project.global.config.datasource

import com.ndgndg91.project.AbstractIntegrationTest
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

class DefaultDataSourceConfigTest: AbstractIntegrationTest() {
    @Autowired
    @Qualifier(DefaultDataSourceConfig.DEFAULT_DATASOURCE)
    private lateinit var dataSource: DataSource

    @Autowired
    @Qualifier(DefaultDataSourceConfig.DEFAULT_HIKARI)
    private lateinit var hikariConfig: HikariConfig

    @Autowired
    @Qualifier(DefaultDataSourceConfig.DEFAULT_TRANSACTION_MANAGER)
    private lateinit var transferManager: PlatformTransactionManager

    @DisplayName("DataSource 빈이 정상적으로 생성되어야 한다")
    @Test
    fun should_create_dataSource_bean() {
        assertThat(dataSource).isNotNull
        assertThat(dataSource).isInstanceOf(HikariDataSource::class.java)

    }

    @DisplayName("HikariConfig 빈이 정상적으로 생성되어야 한다")
    @Test
    fun should_create_hikariConfig_bean() {
        assertThat(hikariConfig).isNotNull
        assertThat(hikariConfig.poolName).isEqualTo(DefaultDataSourceConfig.DEFAULT_DATASOURCE)
    }


    @DisplayName("TransactionManager 빈이 정상적으로 생성되어야 한다")
    @Test
    fun should_create_transactionManager_bean() {
        assertThat(transferManager).isNotNull
        assertThat(transferManager).isInstanceOf(DataSourceTransactionManager::class.java)
    }

}