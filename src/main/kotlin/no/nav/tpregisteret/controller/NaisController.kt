package no.nav.tpregisteret.controller

import no.nav.security.token.support.core.api.Unprotected
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Unprotected
@RestController
class NaisController {

    @get:GetMapping("/isAlive")
    val isAlive = true

    @get:GetMapping("/isReady")
    val isReady = true
}