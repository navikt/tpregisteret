package no.nav.tpregisteret.controller

import no.nav.tpregisteret.domain.Person
import no.nav.tpregisteret.repository.PersonRepository
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/person")
class PersonController(private val personRepository: PersonRepository) {

    @GetMapping("/tpordninger")
    fun getTpOrdningerForPerson(@RequestHeader("fnr") fnr: String) = personRepository.getTpOrdningerForPerson(fnr)

    @GetMapping("/forhold")
    fun getForholdForPerson(
            @RequestHeader("fnr") fnr: String,
            @RequestHeader("tpId") tpId: String
    ) = personRepository.getForholdByFnrAndTpNr(fnr, tpId)

    @GetMapping("/ytelser")
    fun getYtelserForPerson(
            @RequestHeader("fnr") fnr: String,
            @RequestHeader("tpId") tpId: String
    ) = personRepository.getAllYtelseByForholdIdAndFnr(fnr, tpId)

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