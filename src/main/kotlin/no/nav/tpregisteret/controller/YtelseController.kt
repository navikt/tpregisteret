package no.nav.tpregisteret.controller

import no.nav.tpregisteret.domain.Ytelse
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/ytelse")
class YtelseController {

    @PostMapping
    @ResponseStatus(CREATED)
    fun lagreNyYtelse(@RequestBody ytelse: Ytelse) = URI(TODO())

    @PatchMapping
    @ResponseStatus(NO_CONTENT)
    fun lagreExistingYtelse(@RequestBody ytelse: Ytelse) {
        TODO()
    }

    @RequestMapping(method = [RequestMethod.HEAD])
    @ResponseStatus(NO_CONTENT)
    fun validateIdenticalYtelse(@RequestHeader("ytelse") ytelse: Ytelse) {
        TODO()
    }

    @GetMapping
    fun hentYtelseMedId(@RequestHeader("TODO") TODO: String) = TODO() as Ytelse

    @PostMapping("/tjenestepensjon")
    @ResponseStatus(CREATED)
    fun lagreTjenestepensjonYtelse(@RequestHeader("tpnr") tpnr: String, @RequestBody ytelse: Ytelse) = URI(TODO())
}