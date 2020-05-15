package no.nav.tpregisteret.repository

import no.nav.tpregisteret.domain.TpOrdning
import no.nav.tpregisteret.support.TestData.TestTpOrdning
import no.nav.tpregisteret.support.TestData.ORG_1
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@DataJpaTest
class OrganisationRepositoryTest {

    @Autowired
    lateinit var organisationRepository: OrganisationRepository

    @Test
    fun `Get all tpOrdning by orgNr`() = with(ORG_1) {
        val tpOrdningList = organisationRepository.findAllByOrgNr(orgNr)
        assertEquals(tpOrdninger.map(TestTpOrdning::tpNr), tpOrdningList.map(TpOrdning::tpNr))
    }

    @Test
    fun `Get empty list of tpordninger from invalid orgNr`() {
        assertTrue(organisationRepository.findAllByOrgNr("12345678910").isEmpty())
    }

    @Test
    fun `Get all tpOrdning by orgNr and tpNr`() = with(ORG_1) {
        val tpOrdningList = organisationRepository.findAllByOrgNrAndTpNr(orgNr, tpOrdninger.first().tpNr)
        assertEquals(tpOrdninger.findFirst().map(TestTpOrdning::tpNr), tpOrdningList.map(TpOrdning::tpNr))
    }

    @Test
    fun getAll() = with(ORG_1) {
        val tpOrdningList = organisationRepository.findAllByOrgNr(orgNr)
//        val tpOrdningList = organisationRepository.findAllByOrgNrAndTpNr(orgNr, tpOrdninger.first().tpNr)
        assertNotNull(tpOrdningList)
    }

}