package no.nav.tpregisteret.security

import no.nav.pensjonsamhandling.maskinporten.validation.orgno.RequestAwareOrganisationValidator
import no.nav.tpregisteret.NAV_ORGNR
import no.nav.tpregisteret.repository.PersonRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class FnrOrgnrValidator(private val personRepository: PersonRepository) :
    RequestAwareOrganisationValidator {
    override fun invoke(orgno: String, o: HttpServletRequest): Boolean {
        LOG.debug("Attempting to validate orgnr {}.", orgno)
        return if (orgno == NAV_ORGNR) {
            LOG.debug("Orgnr belongs to NAV.")
            true
        } else {
            val fnr = o.getHeader("fnr")
            LOG.debug("Validating against fnr {}.", fnr.take(6)+"*****")
            personRepository.findByFnr(fnr)?.forhold?.any { it.tpOrdning.orgNr == orgno } ?: false
        }.also { LOG.debug("Accepted: {}", it) }
    }

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(YtelseIdOrgnrValidator::class.java)
    }
}