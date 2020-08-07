package no.nav.tpregisteret.exceptions

import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.server.ResponseStatusException

sealed class ResursIkkeFunnet(resurs: String):
        ResponseStatusException(NOT_FOUND, "$resurs ikke funnet.")

class TpOrdningIkkeFunnet: ResursIkkeFunnet("TP-ordning")
class PersonIkkeFunnet: ResursIkkeFunnet("Person")
class SimuleringIkkeFunnet: ResursIkkeFunnet("Simulering")
class YtelseIkkeFunnet: ResursIkkeFunnet("Ytelse")
class ForholdIkkeFunnet: ResursIkkeFunnet("Forhold")