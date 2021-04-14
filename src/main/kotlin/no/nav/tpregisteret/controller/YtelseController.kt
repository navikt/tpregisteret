package no.nav.tpregisteret.controller

import no.nav.pensjonsamhandling.maskinporten.validation.annotation.Maskinporten
import no.nav.tpregisteret.TPREGISTERET_SCOPE
import no.nav.tpregisteret.domain.Ytelse
import no.nav.tpregisteret.security.YtelseIdOrgnrValidator
import no.nav.tpregisteret.service.YtelseService
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/ytelse")
class YtelseController(private val ytelseService: YtelseService) {

    @PostMapping
    @ResponseStatus(CREATED)
    fun saveYtelse(
        @RequestBody ytelse: Ytelse
    ): URI = TODO()

    @PatchMapping
    @ResponseStatus(NO_CONTENT)
    fun updateYtelse(
        @RequestBody ytelse: Ytelse
    ) {
        TODO()
    }

    @GetMapping("/exists")
    @ResponseStatus(NO_CONTENT)
    fun validateIdenticalYtelse(
        @RequestBody ytelse: Ytelse
    ) = ytelseService.checkYtelse(ytelse)

    @Maskinporten(TPREGISTERET_SCOPE, YtelseIdOrgnrValidator::class)
    @GetMapping
    fun getYtelse(
        @RequestHeader("ytelseId") id: Long
    ) = ytelseService.getYtelseById(id)

    @PostMapping("/tjenestepensjon")
    @ResponseStatus(CREATED)
    fun saveTjenestepensjonYtelse(
        @RequestHeader("tpId") tpId: String,
        @RequestBody ytelse: Ytelse
    ): URI = TODO()
}