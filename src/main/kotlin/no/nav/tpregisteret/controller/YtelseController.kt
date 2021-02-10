package no.nav.tpregisteret.controller

import no.nav.pensjonsamhandling.maskinporten.validation.annotation.Maskinporten
import no.nav.tpregisteret.TPREGISTERET_SCOPE
import no.nav.tpregisteret.domain.dto.YtelseDto
import no.nav.tpregisteret.security.YtelseIdOrgnrValidator
import no.nav.tpregisteret.service.YtelseService
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.HEAD
import java.net.URI

@RestController
@RequestMapping("/ytelse")
class YtelseController(private val ytelseService: YtelseService) {

    @PostMapping
    @ResponseStatus(CREATED)
    fun saveYtelse(
            @RequestBody ytelse: YtelseDto
    ) = URI(TODO())

    @PatchMapping
    @ResponseStatus(NO_CONTENT)
    fun updateYtelse(
            @RequestBody ytelse: YtelseDto
    ) {
        TODO()
    }

    @RequestMapping(method = [HEAD])
    @ResponseStatus(NO_CONTENT)
    fun validateIdenticalYtelse(
            @RequestHeader("ytelse") ytelse: YtelseDto
    ) = ytelseService.hasYtelse(ytelse)

    @Maskinporten(TPREGISTERET_SCOPE, YtelseIdOrgnrValidator::class)
    @GetMapping
    fun getYtelse(
            @RequestHeader("ytelseId") id: Long
    ) = YtelseDto(ytelseService.getYtelseById(id))

    @PostMapping("/tjenestepensjon")
    @ResponseStatus(CREATED)
    fun saveTjenestepensjonYtelse(
            @RequestHeader("tpId") tpId: String,
            @RequestBody ytelse: YtelseDto
    ) = URI(TODO())
}