package no.nav.tpregisteret.controller

import no.nav.tpregisteret.domain.TpOrdning
import org.springframework.http.ResponseEntity

import no.nav.tpregisteret.tpordning.TpRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/person")
class PersonController(private val tpRepository: TpRepository) {

    @GetMapping("/{fnr}/tpordninger")
    fun getTpOrdningerForPerson(@PathVariable("fnr") fnr : String) : ResponseEntity<List<TpOrdning>>
            = ok(tpRepository.getTpOrdningerForPerson(fnr))

    @DeleteMapping("/{fnr}")
    fun deletePerson(@PathVariable("fnr") fnr : String) : ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.OK)
    }

    @PatchMapping("/{fnr}")
    fun updatePerson(@PathVariable("fnr") fnr : String) : ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.OK)
    }
}