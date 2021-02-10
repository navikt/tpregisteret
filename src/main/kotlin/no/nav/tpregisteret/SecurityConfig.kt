package no.nav.tpregisteret

import no.nav.pensjonsamhandling.maskinporten.validation.annotation.EnableMaskinportenValidation
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("!test")
@EnableMaskinportenValidation
class SecurityConfig