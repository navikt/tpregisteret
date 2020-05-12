package no.nav.tpregisteret.dto

import no.nav.tpregisteret.domain.Ytelse

class YtelseDto(ytelse: Ytelse) {
    val id = ytelse.id
    val fnr = ytelse.forhold.person.fnr
    val datoFom = ytelse.datoFom
    val datoTom = ytelse.datoTom
}