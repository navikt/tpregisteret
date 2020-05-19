package no.nav.tpregisteret.domain.dto

import no.nav.tpregisteret.domain.TpOrdning

data class TpOrdningDto(
        val tssId: String,
        val tpId: String,
        val orgNr: String,
        val navn: String
) {
    constructor(tp: TpOrdning) : this(
            tp.tssId,
            tp.tpId,
            tp.orgNr,
            tp.navn
    )
}