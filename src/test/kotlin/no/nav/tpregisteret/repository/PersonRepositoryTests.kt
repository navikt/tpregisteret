package no.nav.tpregisteret.repository

import no.nav.tpregisteret.TestPerson.Companion.testPerson2
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@DataJpaTest
class PersonRepositoryTests {

    @Autowired
    lateinit var personRepository: PersonRepository

    @Test
    fun `Get existing person by FNR`() = with(testPerson2) {
        val person = personRepository.findByFnr(fnr)
        assertNotNull(person)
        assertEquals(fnr, person.fnr)
    }
}