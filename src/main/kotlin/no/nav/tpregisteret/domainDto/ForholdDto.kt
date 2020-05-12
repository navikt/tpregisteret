package no.nav.tpregisteret.domainDto

import no.nav.tpregisteret.domain.Forhold
import java.time.LocalDate

class ForholdDto {
    var id: Long = 0
    lateinit var person: PersonDto
    lateinit var tpOrdning: TpOrdningDto
    var ytelser: List<YtelseDto>
    var datoFom: LocalDate
    var datoTom: LocalDate

    constructor(forhold: Forhold) {
        this.id = forhold.id
        this.datoFom = forhold.datoFom
        this.datoTom = forhold.datoTom
        this.ytelser = forhold.ytelser.map{ YtelseDto(it) }
    }
}