package no.nav.tpregisteret.repository

import no.nav.tpregisteret.domain.Person
import org.springframework.data.jpa.repository.JpaRepository

interface PersonRepository: JpaRepository<Person, Number>{
    fun findByFnr(fnr: String): Person?
}