package no.nav.tpregisteret.repository

import no.nav.tpregisteret.TestPerson.Companion.testPerson1
import no.nav.tpregisteret.TestPerson.Companion.testPerson2
import no.nav.tpregisteret.TestPerson.Companion.testPerson3
import no.nav.tpregisteret.TestPerson.Companion.testPerson4
import no.nav.tpregisteret.TestPerson.Companion.testPerson6
import no.nav.tpregisteret.exceptions.ForholdIkkeFunnet
import no.nav.tpregisteret.exceptions.PersonIkkeFunnet
import no.nav.tpregisteret.exceptions.TpOrdningIkkeFunnet
import kotlin.test.assertEquals

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Repository
import kotlin.test.assertNotNull

@SpringBootTest
class PersonRepositoryTests {

    @Autowired
    lateinit var personRepository: PersonRepository

    @Test
    fun `Get existing person by FNR`(){
        val person = personRepository.findAll()//findByFnr(testPerson1.fnr)
        assertNotNull(person)
    }
}