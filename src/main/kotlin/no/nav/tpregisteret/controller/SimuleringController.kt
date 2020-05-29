package no.nav.tpregisteret.controller

import no.nav.tpregisteret.domain.dto.SimuleringDto
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*
import java.net.URI

@Suppress("UNREACHABLE_CODE")
@RestController
@RequestMapping("/simulering")
class SimuleringController {

    @PostMapping
    @ResponseStatus(CREATED)
    fun lagreNySimulering(@RequestBody simulering: SimuleringDto) = URI(TODO())

    @PatchMapping
    @ResponseStatus(NO_CONTENT)
    fun lagreEksisterendeSimulering(@RequestBody simulering: SimuleringDto) { TODO() }

    @GetMapping
    fun findSimulering(@RequestHeader("fnr") fnr: String) = TODO() as SimuleringDto

    @PostMapping("/tjenestepensjon")
    @ResponseStatus(CREATED)
    fun lagreTjenestepensjonSimulering(@RequestHeader("tpId") tpId: String, @RequestBody simulering: SimuleringDto) = URI(TODO())
}