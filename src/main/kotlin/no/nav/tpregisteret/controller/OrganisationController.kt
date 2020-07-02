package no.nav.tpregisteret.controller

import no.nav.security.token.support.core.api.Protected
import no.nav.security.token.support.core.api.Unprotected
import no.nav.tpregisteret.service.OrganisationService
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/organisation")
class OrganisationController(private val organisationService: OrganisationService) {

    @GetMapping
    @Unprotected
    @ResponseStatus(NO_CONTENT)
    fun checkTpOrdningInOrg(
            @RequestHeader("orgNr") orgNr: String,
            @RequestHeader("tpId") tpId: String
    ) = organisationService.hasTpIdInOrg(orgNr, tpId)

    @Protected
    @GetMapping("/orgnr")
    fun getOrganisationByTSSId(
            @RequestHeader("tssId") tssId: String
    ) = organisationService.getOrgNrByTssId(tssId)

    @Protected
    @GetMapping("/navn")
    fun getOrganisationName(
            @RequestHeader("orgNr") orgNr: String
    ) = organisationService.getNameByOrgNr(orgNr)
}