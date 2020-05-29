package no.nav.tpregisteret.exceptions

import io.prometheus.client.Counter
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
class ExceptionHandler {

    internal class InternalException: Throwable()

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(ExceptionHandler::class.java)
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
    fun emptyResultFromRepository(e : EmptyResultDataAccessException): ResponseEntity<Nothing?>
            = ResponseEntity.notFound().build()

    @ExceptionHandler(ResursIkkeFunnet::class)
    fun resursIkkeFunnet(e : ResursIkkeFunnet): ResponseEntity<Nothing?>
            = ResponseEntity.notFound().header("resurs", e.resource).build()

    @ExceptionHandler(NotImplementedError::class)
    @ResponseStatus(NOT_IMPLEMENTED)
    fun notImplemented(e : NotImplementedError) {/*No body*/}
}