package no.nav.tpregisteret.dto

import no.nav.tpregisteret.domain.TpOrdning

class TpOrdningDto {
    var id: String
    var tpNr: String
    var orgnr: String
    var navn: String

    constructor(tp: TpOrdning) {
        this.id = tp.id
        this.tpNr = tp.tpNr
        this.orgnr = tp.orgnr
        this.navn = tp.navn
    }
}