package no.nav.tpregisteret.domain.dto

import no.nav.tpregisteret.domain.Forhold
import no.nav.tpregisteret.domain.Ytelse
import java.time.LocalDate

data class ForholdDto(
        val id: Long,
        val fnr: String,
        val tpId: String,
        val ytelser: List<Long>,
        val datoFom: LocalDate,
        val datoTom: LocalDate?
) {
    constructor(forhold: Forhold) : this(
            forhold.id,
            forhold.person.fnr,
            forhold.tpOrdning.tpId,
            forhold.ytelser.map(Ytelse::id),
            forhold.datoFom,
            forhold.datoTom
    )
}