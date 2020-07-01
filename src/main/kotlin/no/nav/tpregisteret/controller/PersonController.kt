package no.nav.tpregisteret.controller

import no.nav.security.token.support.core.api.Protected
import no.nav.tpregisteret.domain.Person
import no.nav.tpregisteret.domain.dto.ForholdDto
import no.nav.tpregisteret.domain.dto.TpOrdningDto
import no.nav.tpregisteret.domain.dto.YtelseDto
import no.nav.tpregisteret.service.PersonService
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/person")
class PersonController(
        val personService: PersonService
) {

    @Protected
    @GetMapping("/tpordninger")
    fun getTpOrdningerForPerson(
            @RequestHeader("fnr") fnr: String
    ) = personService.getTpOrdningerForPerson(fnr).map(::TpOrdningDto)

    @Protected
    @GetMapping("/forhold")
    fun getForholdForPerson(
            @RequestHeader("fnr") fnr: String,
            @RequestHeader("tpId") tpId: String
    ) = ForholdDto(personService.getForholdForPerson(fnr, tpId))

    @Protected
    @GetMapping("/ytelser")
    fun getYtelserForPerson(
            @RequestHeader("fnr") fnr: String,
            @RequestHeader("tpId") tpId: String
    ) = personService.getYtelserForPerson(fnr, tpId).map(::YtelseDto)

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    fun deletePerson(
            @RequestHeader("fnr") fnr: String
    ) {
        TODO()
    }

    @PatchMapping
    @ResponseStatus(NO_CONTENT)
    fun updatePerson(
            @RequestBody person: Person
    ) {
        TODO()
    }
}