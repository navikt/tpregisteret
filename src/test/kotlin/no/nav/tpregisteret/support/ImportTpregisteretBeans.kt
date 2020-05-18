package no.nav.tpregisteret.support

import no.nav.tpregisteret.service.OrganisationService
import no.nav.tpregisteret.service.PersonService
import no.nav.tpregisteret.service.YtelseService
import org.springframework.context.annotation.Import

@Import(TestSecurityConfig::class,
        PersonService::class,
        OrganisationService::class,
        YtelseService::class)
annotation class ImportTpregisteretBeans