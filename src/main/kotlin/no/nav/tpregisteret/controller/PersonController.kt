package no.nav.tpregisteret.controller

import no.nav.tpregisteret.domain.Person
import no.nav.tpregisteret.domainDto.TpOrdningDto
import no.nav.tpregisteret.service.PersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/person")
class PersonController(

) {
    @Autowired
    private lateinit var personService: PersonService

    @GetMapping("/tpordninger")
    fun getTpOrdningerForPerson(@RequestHeader("fnr") fnr: String): List<TpOrdningDto> =
            personService.getTpOrdningerForPerson(fnr)

    @GetMapping("/forhold")
    fun getForholdForPerson(
            @RequestHeader("fnr") fnr: String,
            @RequestHeader("tpId") tpId: String
    ) = personService.getForholdForPerson(fnr, tpId)

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