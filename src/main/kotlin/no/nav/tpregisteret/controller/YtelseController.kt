package no.nav.tpregisteret.controller

import no.nav.tpregisteret.domain.Ytelse
import no.nav.tpregisteret.tpordning.TpRepository
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/ytelse")
class YtelseController(private val tpRepository: TpRepository) {

    @PostMapping
    fun lagreTjenestepensjonYtelse(@RequestHeader("TODO") TODO: String): ResponseEntity<URI> = created(TODO()).build()

    @PostMapping
    fun lagreNyYtelse(@RequestHeader("TODO") TODO: String): ResponseEntity<URI> = created(TODO()).build()

    @PatchMapping
    fun lagreExistingYtelse(@RequestHeader("TODO") TODO: String): ResponseEntity<Nothing?> = noContent().build()

    @RequestMapping(method = [RequestMethod.HEAD])
    fun validateIdenticalYtelse(@RequestHeader("ytelse") ytelse: Ytelse): ResponseEntity<Nothing?> = noContent().build()

    @GetMapping
    fun hentYtelseMedId(@RequestHeader("TODO") TODO: String): ResponseEntity<Nothing?> = ok(TODO())
}