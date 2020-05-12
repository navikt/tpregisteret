package no.nav.tpregisteret.service

import no.nav.tpregisteret.domainDto.ForholdDto
import no.nav.tpregisteret.domainDto.PersonDto
import no.nav.tpregisteret.domainDto.TpOrdningDto
import no.nav.tpregisteret.exceptions.ForholdIkkeFunnet
import no.nav.tpregisteret.exceptions.PersonIkkeFunnet
import no.nav.tpregisteret.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PersonService {

    @Autowired
    private lateinit var personRepository: PersonRepository

    fun findPersonByFnr(fnr: String) =
            PersonDto(personRepository.findByFnr(fnr) ?: throw PersonIkkeFunnet())


    fun getTpOrdningerForPerson(fnr: String): List<TpOrdningDto> =
            findPersonByFnr(fnr).forhold.map(ForholdDto::tpOrdning)

    fun getForholdForPerson(fnr: String, tpId: String) =
        findPersonByFnr(fnr).forhold.firstOrNull { it.tpOrdning.tpNr == tpId } ?: throw ForholdIkkeFunnet()
}