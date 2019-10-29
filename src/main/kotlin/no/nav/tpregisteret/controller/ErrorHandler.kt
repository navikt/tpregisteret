package no.nav.tpregisteret.controller

import no.nav.tpregisteret.exceptions.ResursIkkeFunnet
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ErrorHandler {

    @ExceptionHandler(ResursIkkeFunnet::class)
    @ResponseStatus(NOT_FOUND)
    fun resursIkkeFunnet() {
    }
}