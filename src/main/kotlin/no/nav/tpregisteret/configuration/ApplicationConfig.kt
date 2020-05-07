package no.nav.tpregisteret.configuration

import org.springframework.beans.factory.BeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
class ApplicationConfig(val beanFactory: BeanFactory) {

    @get:Bean
    val myDataSource: DataSource = EmbeddedDatabaseBuilder().setType(H2).build()

    @get:Bean
    val entityManagerFactory: EntityManagerFactory = LocalContainerEntityManagerFactoryBean().apply {
        jpaVendorAdapter = HibernateJpaVendorAdapter()
                .apply { setGenerateDdl(true) }
        setPackagesToScan("no.nav.tpregisteret.domain")
        setJtaDataSource(myDataSource)
        afterPropertiesSet()
    }.`object`!!

    @get:Bean
    val transactionManager = JpaTransactionManager().also { it.entityManagerFactory = entityManagerFactory }
}