package no.nav.tpregisteret.controller

import no.nav.security.token.support.core.api.Protected
import no.nav.security.token.support.core.api.ProtectedWithClaims
import no.nav.tpregisteret.SCOPE_KEY
import no.nav.tpregisteret.TPREGISTERET_SCOPE
import no.nav.tpregisteret.domain.dto.ForholdDto
import no.nav.tpregisteret.domain.dto.PersonDto
import no.nav.tpregisteret.domain.dto.YtelseDto
import no.nav.tpregisteret.service.PersonService
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/person")
@ProtectedWithClaims(issuer = "maskinporten", claimMap = ["$SCOPE_KEY=$TPREGISTERET_SCOPE"])
class PersonController(
        val personService: PersonService
) {

    @GetMapping("/tpordninger")
    fun getTpOrdningerForPerson(
            @RequestHeader("fnr") fnr: String
    ) = personService.getTpOrdningerForPerson(fnr)

    @GetMapping("/tpordninger/intern")
    @ProtectedWithClaims(issuer = "sts", claimMap = ["iss=sts"])
    fun internGetTpOrdningerForPerson(
            @RequestHeader("fnr") fnr: String
    ) = personService.getTpOrdningerForPerson(fnr)

    @GetMapping("/forhold")
    fun getForholdForPerson(
            @RequestHeader("fnr") fnr: String,
            @RequestHeader("tpId") tpId: String
    ) = ForholdDto(personService.getForholdForPerson(fnr, tpId))

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
            @RequestBody person: PersonDto
    ) {
        TODO()
    }
}