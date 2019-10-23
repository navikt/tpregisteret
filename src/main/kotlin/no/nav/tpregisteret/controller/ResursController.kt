package no.nav.tpregisteret.controller

import no.nav.tpregisteret.exceptions.ResursIkkeFunnet
import no.nav.tpregisteret.tpordning.TpRepository
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.notFound
import org.springframework.web.bind.annotation.ExceptionHandler

abstract class ResursController(internal val tpRepository: TpRepository) {

    @ExceptionHandler(ResursIkkeFunnet::class)
    fun resursIkkeFunnet(): ResponseEntity<Nothing?> = notFound().build()
}