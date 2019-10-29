package no.nav.tpregisteret.controller

import no.nav.tpregisteret.domain.Forhold
import no.nav.tpregisteret.domain.Simulering
import no.nav.tpregisteret.domain.Ytelse
import no.nav.tpregisteret.tpordning.TpRepository
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/tjenestepensjon")
class TjenestepensjonController(tpRepository: TpRepository) : ResursController(tpRepository) {

    /**
     * Overordnede endepunkter for hele tjenestepensjon.
     */
    @GetMapping
    fun hentTjenestePensjon(@RequestHeader("tpnr") tpnr: String) = TODO() as ClassNotFoundException

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    fun slettTjenestePensjon(@RequestHeader("tpnr") tpnr: String) {
        TODO()
    }

    @DeleteMapping("/TBD")
    @ResponseStatus(NO_CONTENT)
    fun slettTjenestePensjonForholdYtelse(@RequestHeader("tpnr") tpnr: String) {
        TODO()
    }

    /**
     * Endepunkter for tjenestepensjon forhold.
     */
    @GetMapping("/forhold")
    fun hentTpforhold(@RequestHeader("forholdId") fnr: String) = TODO() as Forhold

    @PostMapping("/forhold")
    @ResponseStatus(CREATED)
    fun lagreTpforhold(@RequestHeader("tpnr") tpnr: String, @RequestBody forhold: Forhold) = URI(TODO())

    /**
     * Endepunkter for tjenestepensjon ytelse.
     */
    @GetMapping("/ytelse")
    fun hentYtelse(@RequestHeader("ytelseId") ytelseId: String) = TODO() as Ytelse

    @PostMapping("/ytelse")
    @ResponseStatus(CREATED)
    fun lagreYtelse(@RequestHeader("tpnr") tpnr: String, @RequestBody ytelse: Ytelse) = URI(TODO())

    /**
     * Endepunkter for tjenestepensjon simulering.
     */
    @GetMapping("/simulering")
    fun hentSimulering(@RequestHeader("simuleringId") simuleringId: String) = TODO() as Simulering

    @PostMapping("/simulering")
    @ResponseStatus(CREATED)
    fun lagreSimulering(@RequestHeader("tpnr") tpnr: String, @RequestBody simulering: Simulering) = URI(TODO())
}
