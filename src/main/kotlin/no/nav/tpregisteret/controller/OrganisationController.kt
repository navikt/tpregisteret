package no.nav.tpregisteret.controller

import no.nav.tpregisteret.domain.TpOrdning
import no.nav.tpregisteret.exceptions.ResursIkkeFunnet
import no.nav.tpregisteret.tpordning.TpRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/organisation")
class OrganisationController(tpRepository: TpRepository) : ResursController(tpRepository) {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(OrganisationController::class.java)
    }

    private fun regexFilter(s: String) = """^[^,]*,[^,]*""".toRegex().find(s)

    @Value("\${orgnr.mapping}")
    lateinit var orgnrMapping: String

    @GetMapping
    fun getTpOrdningerForPerson(
            @RequestHeader("orgnr") orgnr: String,
            @RequestHeader("tpnr") tpnr: String
    ): ResponseEntity<List<TpOrdning>> = when {
        validVaultOrgnrMapping(orgnr, tpnr) -> handleValidMapping(orgnr, tpnr)
        tpRepository.getTpNrsForOrganisation(orgnr).contains(tpnr) -> ok(emptyList())
        else -> throw ResursIkkeFunnet()
    }

    private fun validVaultOrgnrMapping(orgnr: String, tpnr: String): Boolean {
        LOG.info("Validate orgnr/tpnr:$orgnr,$tpnr")
        return orgnrMapping.split("\\|")
                .mapNotNull(::regexFilter)
                .map(MatchResult::value)
                .onEach { LOG.info("Vault mapping: $it") }
                .any { it == "$orgnr,$tpnr" }
    }

    private fun handleValidMapping(orgnr: String, tpnr: String): ResponseEntity<List<TpOrdning>> {
        LOG.info("Valid vault mapping: orgnr $orgnr for tpnr $tpnr")
        return ok(emptyList())
    }
}