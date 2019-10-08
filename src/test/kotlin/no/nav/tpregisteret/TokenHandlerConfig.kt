package no.nav.tpregisteret

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import no.nav.tokentest.TokenHandler

@Configuration
class TokenHandlerConfig {
    @Bean
    fun tokenHandler(): TokenHandler = TokenHandler()
}
