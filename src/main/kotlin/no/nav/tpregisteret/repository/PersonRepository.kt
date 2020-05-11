package no.nav.tpregisteret.repository

import no.nav.tpregisteret.domain.Person
import no.nav.tpregisteret.exceptions.PersonIkkeFunnet
import org.springframework.data.repository.CrudRepository

interface PersonRepository: CrudRepository<Person, Number> {
    fun findByFnr(fnr: String): Person?


    fun findForholdByTpnrAndFnr(fnr: String, tpnr: String) {
        val person = findByFnr(fnr) ?: throw PersonIkkeFunnet
                .forhold.first{ it.tpOrdning.tpNr == tpnr }
    }
}