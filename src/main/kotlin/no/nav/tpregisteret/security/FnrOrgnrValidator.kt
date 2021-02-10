package no.nav.tpregisteret.security

import no.nav.pensjonsamhandling.maskinporten.validation.orgno.RequestAwareOrganisationValidator
import no.nav.tpregisteret.NAV_ORGNR
import no.nav.tpregisteret.repository.PersonRepository
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class FnrOrgnrValidator(private val personRepository: PersonRepository) :
    RequestAwareOrganisationValidator {
    override fun invoke(orgno: String, o: HttpServletRequest) = orgno == NAV_ORGNR
            || personRepository.findByFnr(o.getHeader("fnr"))?.forhold?.any { it.tpOrdning.orgNr == orgno } ?: false
}