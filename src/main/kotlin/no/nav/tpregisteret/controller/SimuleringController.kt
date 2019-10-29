package no.nav.tpregisteret.controller

import no.nav.tpregisteret.domain.Simulering
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/simulering")
class SimuleringController {

    @PostMapping
    @ResponseStatus(CREATED)
    fun lagreNySimulering(@RequestBody simulering: Simulering) = URI(TODO())

    @PatchMapping
    @ResponseStatus(NO_CONTENT)
    fun lagreEksisterendeSimulering(@RequestBody simulering: Simulering) {
    }

    @GetMapping
    fun findSimulering(@RequestHeader("fnr") fnr: String) = TODO() as Simulering

    @PostMapping("/tjenestepensjon")
    @ResponseStatus(CREATED)
    fun lagreTjenestepensjonSimulering(@RequestHeader("tpnr") tpnr: String, @RequestBody simulering: Simulering) = URI(TODO())
}