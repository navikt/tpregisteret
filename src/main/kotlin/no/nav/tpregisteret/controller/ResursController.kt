package no.nav.tpregisteret.controller

import no.nav.tpregisteret.exceptions.ResursIkkeFunnet
import no.nav.tpregisteret.tpordning.TpRepository
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

abstract class ResursController(internal val tpRepository: TpRepository) {

    @ExceptionHandler(ResursIkkeFunnet::class)
    @ResponseStatus(NOT_FOUND)
    fun resursIkkeFunnet() {
    }
}