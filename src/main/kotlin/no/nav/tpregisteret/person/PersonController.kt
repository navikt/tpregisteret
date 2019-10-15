package no.nav.tpregisteret.person

import no.nav.tpregisteret.tpordning.TpOrdning
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import no.nav.tpregisteret.tpordning.TpRepository
import org.springframework.http.ResponseEntity.ok

@RestController
@RequestMapping("/person")
class PersonController(private val tpRepository: TpRepository) {

    @GetMapping("/{fnr}/tpordninger")
    fun getTpOrdningerForPerson(@PathVariable("fnr") fnr : String) : ResponseEntity<List<TpOrdning>>
            = ok(tpRepository.getTpOrdningerForPerson(fnr))
}