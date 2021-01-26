package no.nav.tpregisteret.controller

import no.nav.tpregisteret.domain.Forhold
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/forhold")
class ForholdController {

    @PostMapping
    @ResponseStatus(CREATED)
    fun saveForhold(
            @RequestBody forhold: Forhold
    ) = forhold

    @PatchMapping
    @ResponseStatus(NO_CONTENT)
    fun updateForhold(
            @RequestBody forhold: Forhold
    ) {
        TODO()
    }

    @GetMapping
    fun finnValidForholdMedId(
            @RequestHeader("TODO") todo: String
    ) = TODO() as Forhold

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    fun deleteForholdCascade(
            @RequestHeader("TODO") todo: String
    ) {
        TODO()
    }

    @PostMapping("/tjenestepensjon")
    @ResponseStatus(CREATED)
    fun saveTjenestepensjonForhold(
            @RequestHeader("tpId") tpId: String,
            @RequestBody forhold: Forhold)
    {
        TODO()
    }
}