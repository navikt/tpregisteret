package no.nav.tpregisteret.support

import no.nav.security.token.support.test.spring.TokenGeneratorConfiguration
import no.nav.tpregisteret.service.OrganisationService
import no.nav.tpregisteret.service.PersonService
import no.nav.tpregisteret.service.YtelseService
import org.springframework.context.annotation.Import

@Import(TokenGeneratorConfiguration::class,
        PersonService::class,
        OrganisationService::class,
        YtelseService::class)
annotation class ImportTpregisteretBeans