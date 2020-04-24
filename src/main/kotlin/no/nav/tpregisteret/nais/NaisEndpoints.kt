package no.nav.tpregisteret.nais

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class NaisEndpoints {

    @get:GetMapping("/isAlive")
    val isAlive = true

    @get:GetMapping("/isReady")
    val isReady = true
}