package com.ndgndg91.project.global.config.datasource

import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import java.util.Properties
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    basePackages = ["com.ndgndg91.project.secondary"],
    entityManagerFactoryRef = "secondaryEntityManagerFactory",
    transactionManagerRef = "secondaryJpaTransactionManager"
)
class SecondaryJpaConfig {

    @Bean
    fun secondaryEntityManagerFactory(
        @Qualifier(SecondaryDataSourceConfig.SECONDARY_DATASOURCE) dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean {
        return LocalContainerEntityManagerFactoryBean().apply {
            this.dataSource = dataSource
            setPackagesToScan("com.ndgndg91.project.secondary")
            jpaVendorAdapter = HibernateJpaVendorAdapter()
            setJpaProperties(Properties().apply {
                setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                setProperty("hibernate.hbm2ddl.auto", "none")
            })
        }
    }

    @Bean
    fun secondaryJpaTransactionManager(
        @Qualifier("secondaryEntityManagerFactory") entityManagerFactory: EntityManagerFactory
    ): PlatformTransactionManager {
        return JpaTransactionManager(entityManagerFactory)
    }
}