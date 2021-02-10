package no.nav.tpregisteret.security

import no.nav.pensjonsamhandling.maskinporten.validation.orgno.RequestAwareOrganisationValidator
import no.nav.tpregisteret.NAV_ORGNR
import no.nav.tpregisteret.repository.YtelseRepository
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class YtelseIdOrgnrValidator(private val ytelseRepository: YtelseRepository) :
    RequestAwareOrganisationValidator {
    override fun invoke(orgno: String, o: HttpServletRequest) = orgno == NAV_ORGNR
            || ytelseRepository.getById(o.getHeader("ytelseId").toLong())?.forhold?.tpOrdning?.orgNr == orgno
}