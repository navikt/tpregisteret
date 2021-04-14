package no.nav.tpregisteret.controller

import no.nav.tpregisteret.domain.Forhold
import no.nav.tpregisteret.domain.TpOrdning
import no.nav.tpregisteret.domain.Ytelse
import no.nav.tpregisteret.domain.Simulering
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/tjenestepensjon")
class TjenestepensjonController {

    /**
     * Overordnede endepunkter for hele tjenestepensjon.
     */
    @GetMapping
    fun getTjenestepensjon(
            @RequestHeader("tpId") tpId: String
    ): TpOrdning = TODO()

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    fun deleteTjenestepensjon(
            @RequestHeader("tpId") tpId: String
    ) {
        TODO()
    }

    @DeleteMapping("/TBD")
    @ResponseStatus(NO_CONTENT)
    fun deleteTjenestepensjonForholdYtelse(
            @RequestHeader("tpId") tpId: String
    ) {
        TODO()
    }

    /**
     * Endepunkter for tjenestepensjon forhold.
     */
    @GetMapping("/forhold")
    fun getTpforhold(
            @RequestHeader("forholdId") forholdId: String
    ): Forhold = TODO()

    @PostMapping("/forhold")
    @ResponseStatus(CREATED)
    fun saveTpforhold(
            @RequestHeader("tpId") tpId: String,
            @RequestBody forhold: Forhold
    ): URI = TODO()

    /**
     * Endepunkter for tjenestepensjon ytelse.
     */
    @GetMapping("/ytelse")
    fun getYtelse(
            @RequestHeader("ytelseId") ytelseId: String
    ): Ytelse = TODO()

    @PostMapping("/ytelse")
    @ResponseStatus(CREATED)
    fun saveYtelse(
            @RequestHeader("tpId") tpId: String,
            @RequestBody ytelse: Ytelse
    ): URI = TODO()

    /**
     * Endepunkter for tjenestepensjon simulering.
     */
    @GetMapping("/simulering")
    fun getSimulering(
            @RequestHeader("simuleringId") simuleringId: String
    ): Simulering = TODO()

    @PostMapping("/simulering")
    @ResponseStatus(CREATED)
    fun saveSimulering(
            @RequestHeader("tpId") tpId: String,
            @RequestBody simulering: Simulering
    ): URI = TODO()
}
