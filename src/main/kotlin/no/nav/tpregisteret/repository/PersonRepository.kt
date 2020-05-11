package no.nav.tpregisteret.repository

import no.nav.tpregisteret.domain.Person
import org.springframework.data.repository.CrudRepository

interface PersonRepository : CrudRepository<Person, Number> {
    fun findByFnr(fnr: String): Person?
}