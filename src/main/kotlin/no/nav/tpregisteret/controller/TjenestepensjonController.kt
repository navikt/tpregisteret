package no.nav.tpregisteret.controller

import no.nav.tpregisteret.domain.Forhold
import no.nav.tpregisteret.domain.TpOrdning
import no.nav.tpregisteret.domain.Ytelse
import no.nav.tpregisteret.domain.dto.SimuleringDto
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
    fun hentTjenestePensjon(
            @RequestHeader("tpId") tpId: String
    ) = TODO() as TpOrdning

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    fun slettTjenestePensjon(
            @RequestHeader("tpId") tpId: String
    ) {
        TODO()
    }

    @DeleteMapping("/TBD")
    @ResponseStatus(NO_CONTENT)
    fun slettTjenestePensjonForholdYtelse(
            @RequestHeader("tpId") tpId: String
    ) {
        TODO()
    }

    /**
     * Endepunkter for tjenestepensjon forhold.
     */
    @GetMapping("/forhold")
    fun hentTpforhold(
            @RequestHeader("forholdId") forholdId: String
    ) = TODO() as Forhold

    @PostMapping("/forhold")
    @ResponseStatus(CREATED)
    fun lagreTpforhold(
            @RequestHeader("tpId") tpId: String,
            @RequestBody forhold: Forhold
    ) = URI(TODO())

    /**
     * Endepunkter for tjenestepensjon ytelse.
     */
    @GetMapping("/ytelse")
    fun hentYtelse(
            @RequestHeader("ytelseId") ytelseId: String
    ) = TODO() as Ytelse

    @PostMapping("/ytelse")
    @ResponseStatus(CREATED)
    fun lagreYtelse(
            @RequestHeader("tpId") tpId: String,
            @RequestBody ytelse: Ytelse
    ) = URI(TODO())

    /**
     * Endepunkter for tjenestepensjon simulering.
     */
    @GetMapping("/simulering")
    fun hentSimulering(
            @RequestHeader("simuleringId") simuleringId: String
    ) = TODO() as SimuleringDto

    @PostMapping("/simulering")
    @ResponseStatus(CREATED)
    fun lagreSimulering(
            @RequestHeader("tpId") tpId: String,
            @RequestBody simulering: SimuleringDto
    ) = URI(TODO())
}
