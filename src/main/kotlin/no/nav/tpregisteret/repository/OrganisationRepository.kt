package no.nav.tpregisteret.repository

import no.nav.tpregisteret.domain.TpOrdning
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OrganisationRepository : CrudRepository<TpOrdning, String> {
    fun findAllByOrgNr(orgNr: String): List<TpOrdning>
    fun existsTpOrdningByOrgNrAndTpId(orgNr: String, tpNr: String): Boolean
}