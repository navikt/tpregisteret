package no.nav.tpregisteret.controller

import no.nav.tpregisteret.domain.Simulering
import no.nav.tpregisteret.tpordning.TpRepository
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/simulering")
class SimuleringController(tpRepository: TpRepository) : ResursController(tpRepository) {

    @PostMapping
    fun lagreNySimulering(@RequestHeader("fnr") fnr: String): ResponseEntity<URI> = created(TODO()).build()

    @PatchMapping
    fun lagreEksisterendeSimulering(@RequestHeader("fnr") fnr: String): ResponseEntity<Nothing?> = noContent().build()

    @GetMapping
    fun findSimulering(@RequestHeader("fnr") fnr: String): ResponseEntity<Simulering> = ok(TODO())

    @PostMapping("/tjenestepensjon")
    fun lagreTjenestepensjonSimulering(@RequestHeader("tpnr") fnr: String): ResponseEntity<URI> = created(TODO()).build()
}