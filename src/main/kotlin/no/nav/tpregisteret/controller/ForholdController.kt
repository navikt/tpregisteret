package no.nav.tpregisteret.controller

import no.nav.tpregisteret.domain.Forhold
import no.nav.tpregisteret.tpordning.TpRepository
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.created
import org.springframework.http.ResponseEntity.noContent
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/forhold")
class ForholdController(private val tpRepository: TpRepository) {

    @PostMapping
    fun lagreNewForhold(@RequestHeader("TODO") TODO: String): ResponseEntity<URI> = created(TODO()).build()

    @PatchMapping
    fun lagreExistingForhold(@RequestHeader("TODO") TODO: String): ResponseEntity<Nothing?> = noContent().build()

    @GetMapping
    fun finnValidForholdMedId(@RequestHeader("TODO") TODO: String): ResponseEntity<Forhold> = created(TODO()).build()

    @DeleteMapping
    fun slettForholdCascade(@RequestHeader("TODO") TODO: String): ResponseEntity<Nothing?> = noContent().build()

    @PostMapping
    fun lagreTjenestepensjonForhold(@RequestHeader("TODO") TODO: String): ResponseEntity<URI> = created(TODO()).build()
}