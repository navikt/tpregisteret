package no.nav.tpregisteret.controller

import no.nav.tpregisteret.domain.Person
import no.nav.tpregisteret.tpordning.TpRepository
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/person")
class PersonController(tpRepository: TpRepository) : ResursController(tpRepository) {

    @GetMapping("/tpordninger")
    fun getTpOrdningerForPerson(@RequestHeader("fnr") fnr: String) = tpRepository.getTpOrdningerForPerson(fnr)

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    fun deletePerson(@RequestHeader("fnr") fnr: String) {
        TODO()
    }

    @PatchMapping
    @ResponseStatus(NO_CONTENT)
    fun updatePerson(@RequestBody person: Person) {
        TODO()
    }
}