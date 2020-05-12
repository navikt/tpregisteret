package no.nav.tpregisteret.domainDto

import no.nav.tpregisteret.domain.Ytelse
import java.time.LocalDate

class YtelseDto(ytelse: Ytelse) {
    val id = ytelse.id
    val fnr = ytelse.forhold.person.fnr
    val datoFom = ytelse.datoFom
    val datoTom = ytelse.datoTom
}