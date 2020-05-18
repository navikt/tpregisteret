package no.nav.tpregisteret.service

import no.nav.tpregisteret.domain.TpOrdning
import no.nav.tpregisteret.exceptions.TpOrdningIkkeFunnet
import no.nav.tpregisteret.repository.OrganisationRepository
import org.springframework.stereotype.Service

@Service
class OrganisationService(val organisationRepository: OrganisationRepository) {

    fun getByOrgNr(orgNr: String) = organisationRepository.findAllByOrgNr(orgNr)
            .ifEmpty { throw TpOrdningIkkeFunnet() }

    fun getNameByOrgNr(orgNr: String) = getByOrgNr(orgNr)
            .map(TpOrdning::navn).toSet()

    fun hasTpIdInOrg(orgNr: String, tpId: String) = if (organisationRepository.existsTpOrdningByOrgNrAndTpId(orgNr, tpId)) Unit
    else throw TpOrdningIkkeFunnet()

    fun getByTssId(tssId: String) = organisationRepository.findById(tssId)
            .orElseGet { throw TpOrdningIkkeFunnet() }!!

    fun getOrgNrByTssId(tssId: String) = getByTssId(tssId).orgNr
}