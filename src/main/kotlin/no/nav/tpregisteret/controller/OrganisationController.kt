package no.nav.tpregisteret.controller

import no.nav.tpregisteret.tpordning.TpRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.notFound
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/organisation")
class OrganisationController(private val tpRepository: TpRepository) {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(OrganisationController::class.java)
    }

    @Value("\${orgnr.mapping}")
    lateinit var orgnrMapping : String

    @GetMapping("/{orgnr}/tpnr/{tpnr}")
    fun getTpOrdningerForPerson(@PathVariable("orgnr") orgnr : String, @PathVariable("tpnr") tpnr : String) : ResponseEntity<Any>
        = when {
            validVaultOrgnrMapping(orgnr, tpnr) -> {
                LOG.info("Valid vault mapping: orgnr $orgnr for tpnr $tpnr")
                ok().build()
            }
            tpRepository.getTpNrsForOrganisation(orgnr).contains(tpnr) -> ok().build()
            else -> notFound().build()
        }

    private fun validVaultOrgnrMapping(orgnr : String, tpnr : String) : Boolean {
        LOG.info("Validate orgnr/tpnr:$orgnr,$tpnr")
        return orgnrMapping.split("\\|")
                .map{ it.split(',').take(2).joinToString(",") }
                .onEach { LOG.info("Vault mapping: $it") }
                .any { it == "$orgnr,$tpnr" }
    }
}