package no.nav.tpregisteret.domain


interface AbstractPersistentDomainObject

data class Simulering(
        val simuleringId: String) : AbstractPersistentDomainObject