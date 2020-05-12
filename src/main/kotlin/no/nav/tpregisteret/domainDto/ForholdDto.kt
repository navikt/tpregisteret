package no.nav.tpregisteret.domainDto

import no.nav.tpregisteret.domain.Forhold
import no.nav.tpregisteret.domain.Ytelse
import java.time.LocalDate

class ForholdDto(forhold: Forhold) {
    val id = forhold.id
    val fnr = forhold.person.fnr
    val tpOrdning = forhold.tpOrdning.tpNr
    val ytelser = forhold.ytelser.map(Ytelse::id)
    val datoFom = forhold.datoFom
    val datoTom = forhold.datoTom
}