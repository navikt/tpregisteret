package no.nav.tpregisteret.controller

import no.nav.tpregisteret.tpordning.TpRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/simulering")
class SimuleringController(tpRepository: TpRepository) : ResursController(tpRepository) {

    @PostMapping
    fun lagreNySimulering(@RequestHeader("fnr") fnr: String) = ""

    @PatchMapping
    fun lagreEksisterendeSimulering(@RequestHeader("fnr") fnr: String) = ""

    @GetMapping
    fun findSimulering(@RequestHeader("fnr") fnr: String) = ""

    @PostMapping("/tjenestepensjon")
    fun lagreTjenestepensjonSimulering(@RequestHeader("tpnr") fnr: String) = ""
}