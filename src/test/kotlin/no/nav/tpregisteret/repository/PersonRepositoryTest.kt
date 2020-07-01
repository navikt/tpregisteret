package no.nav.tpregisteret.repository

import no.nav.security.token.support.test.spring.TokenGeneratorConfiguration
import no.nav.tpregisteret.support.TestData.PERSON_2
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@DataJpaTest
@Import(TokenGeneratorConfiguration::class)
class PersonRepositoryTest {

    @Autowired
    lateinit var personRepository: PersonRepository

    @Test
    fun `Get existing person by FNR`() = with(PERSON_2) {
        val person = personRepository.findByFnr(fnr)
        assertNotNull(person)
        assertEquals(fnr, person.fnr)
    }

    @Test
    fun `Return null when person not found by fnr` () {
        assertNull(personRepository.findByFnr("123545678910"))
    }
}