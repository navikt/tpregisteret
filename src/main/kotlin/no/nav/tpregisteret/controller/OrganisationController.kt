package no.nav.tpregisteret.controller

import no.nav.tpregisteret.domain.TpOrdning
import no.nav.tpregisteret.exceptions.ResursIkkeFunnet
import no.nav.tpregisteret.tpordning.TpRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/organisation")
class OrganisationController(tpRepository: TpRepository) : ResursController(tpRepository) {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(OrganisationController::class.java)
    }

    private val regex = """\d{9},\d{4}""".toRegex()

    @Value("\${orgnr.mapping}")
    lateinit var orgnrMapping: String

    @GetMapping
    fun getTpOrdningerForPerson(
            @RequestHeader("orgnr") orgnr: String,
            @RequestHeader("tpnr") tpnr: String
    ) = when {
        validVaultOrgnrMapping(orgnr, tpnr) -> handleValidMapping(orgnr, tpnr)
        tpRepository.getTpNrsForOrganisation(orgnr).contains(tpnr) -> emptyList()
        else -> throw ResursIkkeFunnet()
    }

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