package no.nav.tpregisteret.domainDto

import no.nav.tpregisteret.domain.Ytelse
import java.time.LocalDate

class YtelseDto {
    var id: Long = 0
    lateinit var forhold: ForholdDto
    lateinit var datoFom: LocalDate
    lateinit var datoTom: LocalDate

    constructor(ytelse: Ytelse) {
        this.id = ytelse.id
        this.datoFom = ytelse.datoFom
        this.datoTom = ytelse.datoTom
    }
}