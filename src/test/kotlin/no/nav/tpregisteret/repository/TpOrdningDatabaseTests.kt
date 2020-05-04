package no.nav.tpregisteret.repository

import no.nav.tpregisteret.TestPerson.Companion.testPerson1
import no.nav.tpregisteret.TestPerson.Companion.testPerson2
import no.nav.tpregisteret.TestPerson.Companion.testPerson3
import no.nav.tpregisteret.TestPerson.Companion.testPerson4
import no.nav.tpregisteret.TestPerson.Companion.testPerson6
import kotlin.test.assertEquals

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TpOrdningDatabaseTests {

    @Autowired
    lateinit var tpRepository : TpRepository

    @Test
    fun ingen_forhold_returnerer_tom_liste() {
        val fnr = testPerson1.fnr

        val expectedtpIds = testPerson1.tpForhold.map { it.tpId }
        val expectedtssIds = testPerson1.tpForhold.map { it.tssId }

        val actualForhold = tpRepository.getTpOrdningerForPerson(fnr)
        
        val actualTpIds = actualForhold.map { it.tpId }
        val actualTssIds = actualForhold.map { it.tssId }

        assertEquals(actualTpIds, (expectedtpIds))
        assertEquals(actualTssIds, (expectedtssIds))
    }

    @Test
    fun ett_forhold_returnerer_ett_tpnummer() {
        val fnr = testPerson2.fnr

        val expectedtpIds = testPerson2.tpForhold.map { it.tpId }
        val expectedtssIds = testPerson2.tpForhold.map { it.tssId }
        val expectedNavn = testPerson2.tpForhold.map { it.navn }

        val actualForhold = tpRepository.getTpOrdningerForPerson(fnr)

        val actualTpIds = actualForhold.map { it.tpId }
        val actualTssIds = actualForhold.map { it.tssId }
        val actualNavn = actualForhold.map { it.navn }

        assertEquals(actualTpIds, (expectedtpIds))
        assertEquals(actualTssIds, (expectedtssIds))
        assertEquals(expectedNavn, (actualNavn))
    }

    @Test
    fun flere_forhold_returnerer_flere_tpnummer() {
        val fnr = testPerson3.fnr

        val expectedtpIds = testPerson3.tpForhold.map { it.tpId }
        val expectedtssIds = testPerson3.tpForhold.map { it.tssId }

        val actualForhold = tpRepository.getTpOrdningerForPerson(fnr)

        val actualTpIds = actualForhold.map { it.tpId }
        val actualTssIds = actualForhold.map { it.tssId }

        assertEquals(actualTpIds, (expectedtpIds))
        assertEquals(actualTssIds, (expectedtssIds))
    }

    @Test
    fun ugyldig_fnr_returnerer_tom_liste() {
        val fnr = "abcdefghijk"

        val expectedtpIds = emptyList<String>()
        val expectedtssIds = emptyList<String>()

        val actualForhold = tpRepository.getTpOrdningerForPerson(fnr)

        val actualTpIds = actualForhold.map { it.tpId }
        val actualTssIds = actualForhold.map { it.tssId }

        assertEquals(actualTpIds, (expectedtpIds))
        assertEquals(actualTssIds, (expectedtssIds))
    }

    @Test
    fun utlands_forhold_ignoreres() {
        val utland = testPerson6.tpForhold
        val fromQuery = tpRepository.getTpOrdningerForPerson(testPerson6.fnr)

        assertEquals(utland.size, 1)
        assertEquals(fromQuery.size, 0)
    }

    @Test
    fun ugyldige_forhold_ignoreres() {
        val invalid = testPerson4.tpForhold
        val fromQuery = tpRepository.getTpOrdningerForPerson(testPerson4.fnr)

        assertEquals(invalid.size, 1)
        assertEquals(fromQuery.size, 0)
    }
}