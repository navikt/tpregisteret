package no.nav.tpregisteret.controller

import no.nav.tpregisteret.tpordning.TpRepository
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/person")
class YtelseController(private val tpRepository: TpRepository) {
//    TODO
//    - lagreTjenestepensjonYtelse
//    - lagreExistingYtelse
//    - validateIdenticalYtelse
//    - lagreNewYtelse
//    - findValidYtelseWithId
}