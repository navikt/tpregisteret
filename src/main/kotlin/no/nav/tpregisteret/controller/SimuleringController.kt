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
    fun saveSimulering(
            @RequestBody simulering: Simulering
    ) = URI(TODO())

    @PatchMapping
    @ResponseStatus(NO_CONTENT)
    fun updateSimulering(
            @RequestBody simulering: Simulering
    ) {
        TODO()
    }

    @GetMapping
    fun getSimulering(
            @RequestHeader("fnr") fnr: String
    ) = TODO() as Simulering

    @PostMapping("/tjenestepensjon")
    @ResponseStatus(CREATED)
    fun saveTjenestepensjonSimulering(
            @RequestHeader("tpId") tpId: String,
            @RequestBody simulering: Simulering
    ) = URI(TODO())
}