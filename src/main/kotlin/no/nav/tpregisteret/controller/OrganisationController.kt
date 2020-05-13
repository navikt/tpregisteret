package no.nav.tpregisteret.controller

import no.nav.tpregisteret.domain.TpOrdning
import no.nav.tpregisteret.service.OrganisationService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/organisation")
class OrganisationController(private val organisationService: OrganisationService) {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(OrganisationController::class.java)
        private val regex = Regex("""\d{9},\d{4}""")
    }

    @Value("\${orgnr.mapping}")
    lateinit var orgnrMapping: String

    @GetMapping
    fun getTpOrdningerForPerson(
            @RequestHeader("orgnr") orgnr: String,
            @RequestHeader("tpnr") tpnr: String
    ) = if (validVaultOrgnrMapping(orgnr, tpnr)) handleValidMapping(orgnr, tpnr)
    else organisationService.getByOrgNrAndTpNr(orgnr, tpnr)

    @GetMapping("/orgnr")
    fun getOrganisationByTSSId(
            @RequestHeader("tssid") tssid: String
    ) = organisationService.getOrgNrByTssId(tssid)

    @GetMapping("/navn")
    fun getOrganisationName(
            @RequestHeader("orgnr") orgnr: String
    ) = organisationService.getNameByOrgNr(orgnr)

    private fun validVaultOrgnrMapping(orgnr: String, tpnr: String): Boolean {
        LOG.info("Validate orgnr/tpnr:$orgnr,$tpnr")
        return orgnrMapping.split('|')
                .mapNotNull { regex.find(it)?.value }
                .onEach { LOG.info("Vault mapping: $it") }
                .any("$orgnr,$tpnr"::equals)
    }

    private fun handleValidMapping(orgnr: String, tpnr: String): List<TpOrdning> {
        LOG.info("Valid vault mapping: orgnr $orgnr for tpnr $tpnr")
        return emptyList()
    }
}
