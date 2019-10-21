package no.nav.tpregisteret.controller

import no.nav.tpregisteret.domain.TpOrdning
import no.nav.tpregisteret.tpordning.TpRepository
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.noContent
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/person")
class PersonController(tpRepository: TpRepository) : ResursController(tpRepository) {

    @GetMapping("/tpordninger")
    fun getTpOrdningerForPerson(@RequestHeader("fnr") fnr: String): ResponseEntity<List<TpOrdning>> = ok(tpRepository.getTpOrdningerForPerson(fnr))

    @DeleteMapping
    fun deletePerson(@RequestHeader("fnr") fnr: String): ResponseEntity<Nothing?> = noContent().build()

    @PatchMapping
    fun updatePerson(@RequestHeader("fnr") fnr: String): ResponseEntity<Nothing?> = noContent().build()
}