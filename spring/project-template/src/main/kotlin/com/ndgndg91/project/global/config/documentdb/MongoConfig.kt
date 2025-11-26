package com.ndgndg91.project.global.config.documentdb

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.connection.ConnectionPoolSettings
import com.mongodb.connection.SocketSettings
import com.mongodb.connection.SslSettings
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.ssl.DefaultSslBundleRegistry
import org.springframework.boot.ssl.SslBundle
import org.springframework.boot.ssl.SslBundles
import org.springframework.boot.ssl.SslStoreBundle
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource
import org.springframework.data.mapping.model.SnakeCaseFieldNamingStrategy
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.MongoManagedTypes
import org.springframework.data.mongodb.MongoTransactionManager
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import org.springframework.data.mongodb.core.mapping.MongoMappingContext
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit


@Configuration
@EnableMongoRepositories(basePackages = ["com.ndgndg91"])
class MongoConfig(
    private val dbProperty: MongoDBProperty,
    private val sslBundlesObjectProvider: ObjectProvider<SslBundles>
) : AbstractMongoClientConfiguration() {

    override fun getDatabaseName(): String {
        return dbProperty.database
    }

    override fun mongoMappingContext(
        customConversions: MongoCustomConversions,
        mongoManagedTypes: MongoManagedTypes
    ): MongoMappingContext {
        val mappingContext = MongoMappingContext()
        mappingContext.setManagedTypes(mongoManagedTypes)
        mappingContext.setSimpleTypeHolder(customConversions.simpleTypeHolder)
        mappingContext.setFieldNamingStrategy(SnakeCaseFieldNamingStrategy())
        mappingContext.isAutoIndexCreation = autoIndexCreation()
        return mappingContext
    }

    @Bean
    @Throws(Exception::class)
    @Profile("default", "dev", "qa", "stage", "prd")
    fun rdsSslStoreBundle(): SslStoreBundle {
        val resource = ClassPathResource(dbProperty.ssl.bundle)
        val inputStream = resource.inputStream

        // CA 번들 파일 로드
        val trustStore: KeyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        trustStore.load(null)

        val cf: CertificateFactory = CertificateFactory.getInstance("X.509")
        val caCerts: Collection<Certificate> = cf.generateCertificates(inputStream)
        for (cert in caCerts) {
            trustStore.setCertificateEntry((cert as X509Certificate).subjectX500Principal.toString(), cert)
        }
        inputStream.close()

        return SslStoreBundle.of(null, null, trustStore)
    }

    @Bean
    @Profile("default", "dev", "qa", "stage", "prd")
    fun rdsSslBundle(rdsSslStoreBundle: SslStoreBundle): SslBundle {
        return SslBundle.of(rdsSslStoreBundle)
    }

    @Bean
    @Profile("default", "dev", "qa", "stage", "prd")
    fun defaultSslBundleRegistry(rdsSslBundle: SslBundle): DefaultSslBundleRegistry {
        val registry = DefaultSslBundleRegistry()
        registry.registerBundle(dbProperty.ssl.bundle, rdsSslBundle)
        return registry
    }

    @Bean
    override fun mongoTemplate(databaseFactory: MongoDatabaseFactory, converter: MappingMongoConverter): MongoTemplate {
        converter.setTypeMapper(DefaultMongoTypeMapper(null))
        return MongoTemplate(databaseFactory, converter)
    }

    @Bean
    fun mongoTransactionManager(databaseFactory: MongoDatabaseFactory): MongoTransactionManager {
        return MongoTransactionManager(databaseFactory)
    }

    override fun configureClientSettings(builder: MongoClientSettings.Builder) {
        builder.applyConnectionString(ConnectionString(dbProperty.uri))
            .uuidRepresentation(dbProperty.uuidRepresentation)
            .applyToConnectionPoolSettings { connectionPoolSettingsBuilder: ConnectionPoolSettings.Builder ->
                connectionPoolSettingsBuilder
                    .maxSize(dbProperty.pool.maxSize)
                    .minSize(dbProperty.pool.minSize)
                    .maxConnectionLifeTime(dbProperty.pool.maxConnectionLifeMin, TimeUnit.MINUTES)
            }
            .applyToSocketSettings { socketSetting: SocketSettings.Builder ->
                socketSetting
                    .connectTimeout(dbProperty.connection.timeoutSec, TimeUnit.SECONDS)
                    .readTimeout(dbProperty.connection.readTimeoutSec, TimeUnit.SECONDS)
            }

        if (dbProperty.ssl.isEnabled) {
            builder.applyToSslSettings { sslSetting: SslSettings.Builder ->
                sslSetting.enabled(true)
                val sslBundles: SslBundles? = sslBundlesObjectProvider.getIfAvailable()
                val sslBundle = sslBundles?.getBundle(dbProperty.ssl.bundle)
                requireNotNull(sslBundle) {
                    throw IllegalStateException("${dbProperty.ssl.bundle} not found.")
                }
                require(sslBundle.options?.isSpecified == false) {
                    throw IllegalStateException("SSL options cannot be specified with MongoDB")
                }
                sslSetting.context(sslBundle.createSslContext())
            }
        }
    }
}