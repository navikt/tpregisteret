package no.nav.tpregisteret.controller

import no.nav.tpregisteret.domain.Ytelse
import no.nav.tpregisteret.domain.dto.YtelseDto
import no.nav.tpregisteret.exceptions.YtelseIkkeFunnet
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
    fun lagreNyYtelse(@RequestBody ytelse: YtelseDto) = URI(TODO())

    @PatchMapping
    @ResponseStatus(NO_CONTENT)
    fun lagreExistingYtelse(@RequestBody ytelse: YtelseDto) {
        TODO()
    }

    @RequestMapping(method = [HEAD])
    @ResponseStatus(NO_CONTENT)
    fun validateIdenticalYtelse(@RequestHeader("ytelse") ytelse: YtelseDto) {
        if (ytelse !in listOf(TODO())) throw YtelseIkkeFunnet()
    }

    @GetMapping
    fun hentYtelseMedId(@RequestHeader("ytelseId") id: Long) = YtelseDto(ytelseService.getYtelseById(id))

    @PostMapping("/tjenestepensjon")
    @ResponseStatus(CREATED)
    fun lagreTjenestepensjonYtelse(@RequestHeader("tpId") tpId: String, @RequestBody ytelse: YtelseDto) = URI(TODO())
}