package no.nav.tpregisteret.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class NaisController {

    @get:GetMapping("/isAlive")
    val isAlive = true

    @get:GetMapping("/isReady")
    val isReady = true
}