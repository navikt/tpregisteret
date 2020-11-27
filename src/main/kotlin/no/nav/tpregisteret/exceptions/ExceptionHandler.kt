package no.nav.tpregisteret.exceptions

import io.prometheus.client.Counter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus


@ControllerAdvice
class ExceptionHandler {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(ExceptionHandler::class.java)
        val errorCounter: Counter = Counter.build()
                .help("Interne feil kastet av TP-registeret.")
                .namespace("tpregisteret")
                .name("internal_server_errors_total")
                .labelNames("exception")
                .register()
    }

    @ExceptionHandler(Throwable::class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun internalServerError(e : Throwable): String? {
        if (e is EmptyResultDataAccessException || e is NotImplementedError || e is ResursIkkeFunnet) throw e
        LOG.error("Something went wrong.", e)
        errorCounter.labels(e::class.simpleName).inc()
        return e.message
    }


    @ExceptionHandler(EmptyResultDataAccessException::class)
    @ResponseStatus(NOT_FOUND)
    fun emptyResultFromRepository(e : EmptyResultDataAccessException) {/*No body*/}

    @ExceptionHandler(NotImplementedError::class)
    @ResponseStatus(NOT_IMPLEMENTED)
    fun notImplemented(e : NotImplementedError) {/*No body*/}
}