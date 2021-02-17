package no.nav.tpregisteret.security

import no.nav.pensjonsamhandling.maskinporten.validation.orgno.RequestAwareOrganisationValidator
import no.nav.tpregisteret.NAV_ORGNR
import no.nav.tpregisteret.repository.YtelseRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class YtelseIdOrgnrValidator(private val ytelseRepository: YtelseRepository) :
    RequestAwareOrganisationValidator {
    override fun invoke(orgno: String, o: HttpServletRequest): Boolean {
        LOG.debug("Attempting to validate orgnr {}.", orgno)
        return if (orgno == NAV_ORGNR) {
            LOG.debug("Orgnr belongs to NAV.")
            true
        } else {
            val ytelseId = o.getHeader("ytelseId").toLong()
            LOG.debug("Validating against ytelseId {}.", ytelseId)
            ytelseRepository.getById(ytelseId)?.forhold?.tpOrdning?.orgNr == orgno
        }.also { LOG.debug("Accepted: {}", it) }
    }

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(YtelseIdOrgnrValidator::class.java)
    }
}