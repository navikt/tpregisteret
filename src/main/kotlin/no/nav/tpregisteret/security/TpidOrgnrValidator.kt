package no.nav.tpregisteret.security

import no.nav.pensjonsamhandling.maskinporten.validation.orgno.RequestAwareOrganisationValidator
import no.nav.tpregisteret.repository.OrganisationRepository
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class TpidOrgnrValidator(private val organisationRepository: OrganisationRepository) :
    RequestAwareOrganisationValidator {
    override fun invoke(orgno: String, o: HttpServletRequest) =
        organisationRepository.existsTpOrdningByOrgNrAndTpId(orgno, o.getHeader("tpId"))
}