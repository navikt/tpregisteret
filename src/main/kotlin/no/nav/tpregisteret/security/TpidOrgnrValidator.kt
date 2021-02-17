package no.nav.tpregisteret.security

import no.nav.pensjonsamhandling.maskinporten.validation.orgno.RequestAwareOrganisationValidator
import no.nav.tpregisteret.NAV_ORGNR
import no.nav.tpregisteret.repository.OrganisationRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class TpidOrgnrValidator(private val organisationRepository: OrganisationRepository) :
    RequestAwareOrganisationValidator {
    override fun invoke(orgno: String, o: HttpServletRequest): Boolean {
        LOG.debug("Attempting to validate orgnr {}.", orgno)
        return if (orgno == NAV_ORGNR) {
            LOG.debug("Orgnr belongs to NAV.")
            true
        } else {
            val tpId = o.getHeader("tpId")
            LOG.debug("Validating against tpId {}.", tpId)
            organisationRepository.existsTpOrdningByOrgNrAndTpId(orgno, tpId)
        }.also { LOG.debug("Accepted: {}", it) }
    }

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(YtelseIdOrgnrValidator::class.java)
    }
}