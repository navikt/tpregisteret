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
import kotlin.test.assertNotNull

@SpringBootTest
class PersonRepositoryTests {

    @Autowired
    lateinit var personRepository: PersonRepository

    @Test
    fun `Throws exception on missing person`() {
        val tpid = testPerson2.tpForhold.first().tpId
        assertThrows<PersonIkkeFunnet> { personRepository.getForholdByFnrAndTpNr("bogus", tpid) }
    }

    @Test
    fun `Throws exception on missing tpordning`() {
        val fnr = testPerson1.fnr
        assertThrows<TpOrdningIkkeFunnet> {
            personRepository.getForholdByFnrAndTpNr(fnr, "bogus")
        }
    }

    @Test
    fun `Returns valid forhold`() {
        with(testPerson3) {
            val tpid = tpForhold.first().tpId
            assertNotNull(personRepository.getForholdByFnrAndTpNr(fnr, tpid))
        }
    }

    @Test
    fun `Throws exception on missing forhold`() {
        with(testPerson1) {
            val tpid = testPerson2.tpForhold.first().tpId
            assertThrows<ForholdIkkeFunnet> {
                personRepository.getForholdByFnrAndTpNr(fnr, tpid)
            }
        }
    }

    @Test
    fun `Returns valid ytelser`() {
        with(testPerson3) {
            val tpid = tpForhold.first().tpId
            val result = personRepository.getAllYtelseByForholdIdAndFnr(fnr, tpid)
            assert(result.size == 2)
        }
    }

    @Test
    fun `Returns empty list of ytelser`() {
        with(testPerson4) {
            val tpid = tpForhold.first().tpId
            val result = personRepository.getAllYtelseByForholdIdAndFnr(fnr, tpid)
            assert(result.isEmpty())
        }
    }

    @Test
    fun `No forhold returns empty list`() {
        val ordnings = personRepository.getTpOrdningerForPerson(testPerson1.fnr)
        assert(ordnings.isEmpty())
    }

    @Test
    fun `One forhold returns one tpid`() {
        with(testPerson2) {
            val expectedOrdnings = tpForhold.toList()
            val actualOrdnings = personRepository.getTpOrdningerForPerson(fnr)

            assert(actualOrdnings.size == 1)
            assertEquals(expectedOrdnings, actualOrdnings)
        }
    }

    @Test
    fun `Multiple forhold returns multiple tpids`() {
        with(testPerson3) {
            val expectedOrdnings = tpForhold.toList()
            val actualOrdnings = personRepository.getTpOrdningerForPerson(fnr)

            assert(actualOrdnings.size > 1)
            assertEquals(expectedOrdnings, actualOrdnings)
        }
    }

    @Test
    fun `Invalid fnr returns empty list`() {
        val ordnings = personRepository.getTpOrdningerForPerson("bogus")
        assert(ordnings.isEmpty())
    }

    @Test
    fun `Utlands forhold are ignored`() {
        with(testPerson6) {
            val utland = tpForhold
            val fromQuery = personRepository.getTpOrdningerForPerson(fnr)

            assertEquals(utland.size, 1)
            assertEquals(fromQuery.size, 0)
        }
    }

    @Test
    fun `Invalid forhold are ignored`() {
        with(testPerson4) {
            val invalid = tpForhold
            val fromQuery = personRepository.getTpOrdningerForPerson(fnr)

            assertEquals(invalid.size, 1)
            assertEquals(fromQuery.size, 0)
        }
    }
}