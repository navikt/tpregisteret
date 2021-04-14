package no.nav.tpregisteret.service

import no.nav.tpregisteret.domain.Ytelse
import no.nav.tpregisteret.exceptions.YtelseIkkeFunnet
import no.nav.tpregisteret.repository.YtelseRepository
import org.springframework.stereotype.Service

@Service
class YtelseService(
    private val ytelseRepository: YtelseRepository
) {
    fun getYtelseById(id: Long) = ytelseRepository.getById(id)
        ?: throw YtelseIkkeFunnet()

    fun checkYtelse(ytelse: Ytelse) {
        if (ytelseRepository.getAllByForholdPersonFnrAndForholdTpOrdningTpId(
                ytelse.forhold.person.fnr,
                ytelse.forhold.tpOrdning.tpId
            ).none(ytelse::equals)
        ) throw YtelseIkkeFunnet()
    }
}