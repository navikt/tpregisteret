package no.nav.tpregisteret.dto

import no.nav.tpregisteret.domain.TpOrdning

data class TpOrdningDto(
        val id: String,
        val tpNr: String,
        val orgnr: String,
        val navn: String
) {
    constructor(tp: TpOrdning) : this(
            tp.id,
            tp.tpNr,
            tp.orgnr,
            tp.navn
    )
}