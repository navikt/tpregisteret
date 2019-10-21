package no.nav.tpregisteret.controller

import no.nav.tpregisteret.domain.Forhold
import no.nav.tpregisteret.domain.Simulering
import no.nav.tpregisteret.domain.Ytelse
import no.nav.tpregisteret.tpordning.TpRepository
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/tjenestepensjon")
class TjenestepensjonController(tpRepository: TpRepository) : ResursController(tpRepository) {

    @GetMapping
    fun hentTjenestePensjon(@RequestHeader("tpnr") tpnr: String): ResponseEntity<ClassNotFoundException> = ok(TODO())

    @DeleteMapping
    fun slettTjenestePensjon(@RequestHeader("tpnr") tpnr: String): ResponseEntity<Nothing?> = noContent().build()

    @DeleteMapping("/TBD")
    fun slettTjenestePensjonForholdYtelse(@RequestHeader("tpnr") tpnr: String): ResponseEntity<Nothing?> = noContent().build()


    @GetMapping("/forhold")
    fun hentTpforhold(@RequestHeader("forholdId") fnr: String): ResponseEntity<Forhold> = ok(TODO())

    @PutMapping("/forhold")
    fun lagreTpforhold(@RequestHeader("forholdId") fnr: String): ResponseEntity<URI> = created(TODO()).build()


    @GetMapping("/ytelse")
    fun hentYtelse(@RequestHeader("ytelseId") ytelseId: String): ResponseEntity<Ytelse> = ok(TODO())

    @PostMapping("/ytelse")
    fun lagreYtelse(@RequestHeader("ytelseId") ytelseId: String): ResponseEntity<URI> = created(TODO()).build()


    @GetMapping("/simulering")
    fun hentSimulering(@RequestHeader("simuleringId") simuleringId: String): ResponseEntity<Simulering> = ok(TODO())

    @PostMapping("/simulering")
    fun lagreSimulering(@RequestHeader("simuleringId") simuleringId: String): ResponseEntity<URI> = created(TODO()).build()
}
