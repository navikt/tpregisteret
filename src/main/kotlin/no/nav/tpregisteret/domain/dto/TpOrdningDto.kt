package no.nav.tpregisteret.domain.dto

import no.nav.tpregisteret.domain.TpOrdning

data class TpOrdningDto(
        val id: String,
        val tpNr: String,
        val orgNr: String,
        val navn: String
) {
    constructor(tp: TpOrdning) : this(
            tp.id,
            tp.tpNr,
            tp.orgNr,
            tp.navn
    )
}