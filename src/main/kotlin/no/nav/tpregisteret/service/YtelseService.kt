package no.nav.tpregisteret.service

import no.nav.tpregisteret.domain.dto.YtelseDto
import no.nav.tpregisteret.exceptions.YtelseIkkeFunnet
import no.nav.tpregisteret.repository.YtelseRepository
import org.springframework.stereotype.Service

@Service
class YtelseService(
        private val ytelseRepository: YtelseRepository
) {
    fun getYtelseById(id: Long) = ytelseRepository.findById(id)
            .orElseThrow { YtelseIkkeFunnet() }!!

    fun hasYtelse(ytelse: YtelseDto) = ytelseRepository.findById(ytelse.id)
        .map { ytelse == YtelseDto(it) }
        .orElseThrow { YtelseIkkeFunnet() }!!
}