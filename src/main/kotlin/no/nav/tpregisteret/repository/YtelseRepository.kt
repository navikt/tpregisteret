package no.nav.tpregisteret.repository

import no.nav.tpregisteret.domain.Ytelse
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface YtelseRepository : CrudRepository<Ytelse, Long> {
    fun getById(id: Long): Ytelse?
}