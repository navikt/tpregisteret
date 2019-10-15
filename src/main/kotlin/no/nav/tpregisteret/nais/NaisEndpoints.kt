package no.nav.tpregisteret.nais

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class NaisEndpoints {

    @GetMapping("/isAlive")
    fun isAlive(): HttpStatus = OK

    @GetMapping("/isReady")
    fun isReady(): HttpStatus = OK
}