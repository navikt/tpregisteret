package no.nav.tpregisteret.controller

import no.nav.tpregisteret.domain.Forhold
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/forhold")
class ForholdController {

    @PostMapping
    @ResponseStatus(CREATED)
    fun lagreNewForhold(@RequestBody forhold: Forhold) = URI(TODO())

    @PatchMapping
    @ResponseStatus(NO_CONTENT)
    fun lagreExistingForhold(@RequestBody forhold: Forhold) {
        TODO()
    }

    @GetMapping
    fun finnValidForholdMedId(@RequestHeader("TODO") TODO: String) = TODO() as Forhold

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    fun slettForholdCascade(@RequestHeader("TODO") TODO: String) {
        TODO()
    }

    @PostMapping("/tjenestepensjon")
    @ResponseStatus(CREATED)
    fun lagreTjenestepensjonForhold(@RequestHeader("tpnr") tpnr: String, @RequestBody forhold: Forhold) {
        TODO()
    }
}