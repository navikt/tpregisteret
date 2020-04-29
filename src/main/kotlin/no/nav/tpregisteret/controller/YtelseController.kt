package no.nav.tpregisteret.controller

import no.nav.tpregisteret.domain.Ytelse
import no.nav.tpregisteret.exceptions.YtelseIkkeFunnet
import no.nav.tpregisteret.tpordning.TpRepository
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.HEAD
import java.net.URI

@RestController
@RequestMapping("/ytelse")
class YtelseController (
        private val tpRepository: TpRepository
) {

    @PostMapping
    @ResponseStatus(CREATED)
    fun lagreNyYtelse(@RequestBody ytelse: Ytelse) = URI(TODO())

    @PatchMapping
    @ResponseStatus(NO_CONTENT)
    fun lagreExistingYtelse(@RequestBody ytelse: Ytelse) {
        TODO()
    }

    @RequestMapping(method = [HEAD])
    @ResponseStatus(NO_CONTENT)
    fun validateIdenticalYtelse(@RequestHeader("ytelse") ytelse: Ytelse) {
        if ( ytelse !in listOf(TODO()) ) throw YtelseIkkeFunnet()
    }

    @GetMapping
    fun hentYtelseMedId(@RequestHeader("ytelseId") ytelseId: String) = tpRepository.getYtelseByID("ytelseId") as Ytelse

    @PostMapping("/tjenestepensjon")
    @ResponseStatus(CREATED)
    fun lagreTjenestepensjonYtelse(@RequestHeader("tpnr") tpnr: String, @RequestBody ytelse: Ytelse) = URI(TODO())
}