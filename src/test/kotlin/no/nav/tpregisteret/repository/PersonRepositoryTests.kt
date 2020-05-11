package no.nav.tpregisteret.repository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.test.assertNotNull

@DataJpaTest
class PersonRepositoryTests {

    @Autowired
    lateinit var personRepository: PersonRepository

    @Test
    fun `Get existing person by FNR`(){
        val person = personRepository.findAll()


        assertNotNull(person)
    }
}