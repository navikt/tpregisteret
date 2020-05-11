package no.nav.tpregisteret.controller

import no.nav.tpregisteret.domain.Forhold
import no.nav.tpregisteret.domain.Person
import no.nav.tpregisteret.domain.TpOrdning
import no.nav.tpregisteret.domain.Ytelse
import no.nav.tpregisteret.exceptions.ForholdIkkeFunnet
import no.nav.tpregisteret.exceptions.PersonIkkeFunnet
import no.nav.tpregisteret.repository.PersonRepository
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/person")
class PersonController(private val personRepository: PersonRepository) {

    @GetMapping("/tpordninger")
    fun getTpOrdningerForPerson(@RequestHeader("fnr") fnr: String): List<TpOrdning> {
        val person = personRepository.findByFnr(fnr) ?: throw PersonIkkeFunnet()
        return person.forhold.map(Forhold::tpOrdning)
    }

    @GetMapping("/forhold")
    fun getForholdForPerson(
            @RequestHeader("fnr") fnr: String,
            @RequestHeader("tpId") tpId: String
    ): Forhold {
        val person = personRepository.findByFnr(fnr) ?: throw PersonIkkeFunnet()
        return person.forhold.firstOrNull { it.tpOrdning.tpNr == tpId } ?: throw ForholdIkkeFunnet()
    }

    @GetMapping("/ytelser")
    fun getYtelserForPerson(
            @RequestHeader("fnr") fnr: String,
            @RequestHeader("tpId") tpId: String
    ) = getForholdForPerson(fnr, tpId).ytelser

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