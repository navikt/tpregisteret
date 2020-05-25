package no.nav.tpregisteret.domain.dto

import no.nav.tpregisteret.domain.Ytelse
import java.time.LocalDate

data class YtelseDto(
        val id: Long,
        val fnr: String,
        val type: String,
        val datoFom: LocalDate,
        val datoTom: LocalDate?,
        val medlemskapDatoFom: LocalDate?
) {
    constructor(ytelse: Ytelse) : this(
            ytelse.id,
            ytelse.forhold.person.fnr,
            ytelse.type,
            ytelse.datoFom,
            ytelse.datoTom,
            ytelse.medlemskapDatoFom
    )
}