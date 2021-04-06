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

    fun hasYtelse(ytelse: Ytelse) = ytelseRepository.getById(ytelse.id)
        ?.equals(ytelse)
        ?: throw YtelseIkkeFunnet()
}