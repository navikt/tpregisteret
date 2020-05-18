package no.nav.tpregisteret

import no.nav.security.token.support.spring.api.EnableJwtTokenValidation
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
@EnableJwtTokenValidation
@SpringBootApplication(scanBasePackages = ["no.nav.tpregisteret"])
class TpregisteretApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<TpregisteretApplication>(*args)
        }
    }
}