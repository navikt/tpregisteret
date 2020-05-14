package no.nav.tpregisteret.controller

import io.prometheus.client.Counter
import no.nav.tpregisteret.exceptions.ResursIkkeFunnet
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_IMPLEMENTED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus


@ControllerAdvice
class ErrorHandler {

    internal class InternalException: Throwable()

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(ErrorHandler::class.java)
        val errorCounter: Counter = Counter.build().help("Interne feil kastet av TP-registeret.").namespace("tpregisteret").name("internal_server_errors_total").labelNames("exception").register()
    }

    @ExceptionHandler(InternalException::class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun internalServerError(e : Exception): String? {
        LOG.error("Something went wrong.", e)
        errorCounter.labels(e::class.simpleName).inc()
        return e.message
    }


    @ExceptionHandler(EmptyResultDataAccessException::class)
    fun emptyResultFromRepository(e : EmptyResultDataAccessException)
            = ResponseEntity.notFound().build<Nothing?>()

    @ExceptionHandler(ResursIkkeFunnet::class)
    fun resursIkkeFunnet(e : ResursIkkeFunnet)
        = ResponseEntity.notFound().header("resurs", e.resource).build<Nothing?>()

    @ExceptionHandler(NotImplementedError::class)
    fun notImplemented(e : NotImplementedError)
            = ResponseEntity.status(NOT_IMPLEMENTED)
}