package no.nav.tpregisteret.exceptions

sealed class ResursIkkeFunnet(val resource : String) : Throwable()

class TpOrdningIkkeFunnet : ResursIkkeFunnet("TP-ordning")
class PersonIkkeFunnet : ResursIkkeFunnet("person")
class SimuleringIkkeFunnet : ResursIkkeFunnet("simulering")
class YtelseIkkeFunnet : ResursIkkeFunnet("ytelse")
class ForholdIkkeFunnet : ResursIkkeFunnet("forhold")