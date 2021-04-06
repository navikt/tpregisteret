package no.nav.tpregisteret.service

import no.nav.tpregisteret.controller.OrganisationController
import no.nav.tpregisteret.domain.TpOrdning
import no.nav.tpregisteret.exceptions.TpOrdningIkkeFunnet
import no.nav.tpregisteret.repository.OrganisationRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class OrganisationService(
    val organisationRepository: OrganisationRepository,
    @param:Value("\${orgnr.mapping}") val orgNrString: String
) {

    @Suppress("LeakingThis")
    private val orgNrMapping = orgNrString.split('|')
        .mapNotNull { regex.find(it)?.value }
        .onEach { LOG.debug("Vault mapping: $it") }

    fun getByOrgNr(orgNr: String) = organisationRepository.findAllByOrgNr(orgNr)
        .ifEmpty { throw TpOrdningIkkeFunnet() }

    fun getNameByOrgNr(orgNr: String) = getByOrgNr(orgNr)
        .map(TpOrdning::navn).toSet()

    fun hasTpIdInOrg(orgNr: String, tpId: String) = when {
        validVaultOrgnrMapping(orgNr, tpId) -> handleValidMapping(orgNr, tpId)
        organisationRepository.existsTpOrdningByOrgNrAndTpId(orgNr, tpId) -> Unit
        else -> throw TpOrdningIkkeFunnet()
    }

    fun getByTssId(tssId: String) = organisationRepository.findById(tssId)
        .orElseGet { throw TpOrdningIkkeFunnet() }!!

    fun getOrgNrByTssId(tssId: String) = getByTssId(tssId).orgNr

    private fun validVaultOrgnrMapping(orgNr: String, tpId: String): Boolean {
        LOG.debug("Validate orgNr/tpId:$orgNr,$tpId")
        return orgNrMapping.any("$orgNr,$tpId"::equals)
    }

    private fun handleValidMapping(orgNr: String, tpId: String) {
        LOG.debug("Valid vault mapping: orgNr $orgNr for tpId $tpId")
    }

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(OrganisationController::class.java)
        private val regex = Regex("""\d{9},\d{4}""")
    }
}