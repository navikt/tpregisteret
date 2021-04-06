package no.nav.tpregisteret.service

import no.nav.tpregisteret.domain.Forhold
import no.nav.tpregisteret.exceptions.ForholdIkkeFunnet
import no.nav.tpregisteret.exceptions.PersonIkkeFunnet
import no.nav.tpregisteret.repository.PersonRepository
import org.springframework.stereotype.Service

@Service
class PersonService(
    val personRepository: PersonRepository
) {

    fun findPersonByFnr(fnr: String) = personRepository.findByFnr(fnr)
        ?: throw PersonIkkeFunnet()


    fun getTpOrdningerForPerson(fnr: String) =
        findPersonByFnr(fnr).forhold.map(Forhold::tpOrdning)

    fun getForholdForPerson(fnr: String, tpId: String) =
        findPersonByFnr(fnr).forhold.firstOrNull { it.tpOrdning.tpId == tpId }
            ?: throw ForholdIkkeFunnet()

    fun getYtelserForPerson(fnr: String, tpId: String) =
        getForholdForPerson(fnr, tpId).ytelser
}