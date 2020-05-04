package no.nav.tpregisteret.domain

import java.time.LocalDate

interface AbstractPersistentDomainObject

data class Person(
        val fnr: String) : AbstractPersistentDomainObject

data class Ytelse(
        val ytelseId: String,
        val fnr: String,
        val fom: LocalDate,
        val tom: LocalDate) : AbstractPersistentDomainObject

data class Forhold(
        val forholdId: String?,
        val fnr: String?,
        val fom: LocalDate?,
        val tom: LocalDate?
) : AbstractPersistentDomainObject

data class Simulering(
        val simuleringId: String) : AbstractPersistentDomainObject

data class TpOrdning(
        var tssId: String = "",
        var tpId: String = "",
        var orgNr: String = "",
        var navn: String = "") : AbstractPersistentDomainObject