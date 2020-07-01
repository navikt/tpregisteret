package no.nav.tpregisteret.repository

import no.nav.security.token.support.test.spring.TokenGeneratorConfiguration
import no.nav.tpregisteret.domain.TpOrdning
import no.nav.tpregisteret.support.TestData.TestTpOrdning
import no.nav.tpregisteret.support.TestData.ORG_1
import no.nav.tpregisteret.support.TestData.ORG_2
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@DataJpaTest
@Import(TokenGeneratorConfiguration::class)
class OrganisationRepositoryTest {

    @Autowired
    lateinit var organisationRepository: OrganisationRepository

    @Test
    fun `Get all tpOrdning by orgNr`() = with(ORG_1) {
        val tpOrdningList = organisationRepository.findAllByOrgNr(orgNr)
        assertEquals(tpOrdninger.map(TestTpOrdning::tpId), tpOrdningList.map(TpOrdning::tpId))
    }

    @Test
    fun `Get empty list of tpordninger from invalid orgNr`() {
        assertTrue(organisationRepository.findAllByOrgNr("12345678910").isEmpty())
    }

    @Test
    fun `Organisation contains TpOrdning`() {
        assert(organisationRepository.existsTpOrdningByOrgNrAndTpId(ORG_1.orgNr, ORG_1.tpOrdninger.first().tpId))
    }

    @Test
    fun `Organisation does not contain TpOrdning`() {
        assertFalse(organisationRepository.existsTpOrdningByOrgNrAndTpId(ORG_1.orgNr, ORG_2.tpOrdninger.first().tpId))
    }

}