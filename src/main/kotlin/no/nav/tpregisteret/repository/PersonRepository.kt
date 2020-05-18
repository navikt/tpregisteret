package no.nav.tpregisteret.repository

import no.nav.tpregisteret.domain.Person
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository : CrudRepository<Person, Long> {
    fun findByFnr(fnr: String): Person?
}