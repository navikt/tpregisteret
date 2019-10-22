package no.nav.tpregisteret.organisation

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.stream.Stream

@SpringBootTest
@AutoConfigureMockMvc
class OrganisationTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    class TestData : ArgumentsProvider {

        @Override
        override fun provideArguments(extensionContext: ExtensionContext): Stream<Arguments> {
            val tpNrA = "1111"
            val tpNrB = "4444"
            val orgNrA = "000000000"
            val orgNrB = "111111111"
            val validVaultMappedTpNrA = "1111"
            val validVaultMappedTpNrB = "3333"
            val validVaultMappedOrgNrA = "222222222"
            val validVaultMappedOrgNrB = "111111111"
            val invalidTpNr = "1234"
            val invalidOrgNr = "123456789"
            val ownTpNr = { status().isOk }
            val doesntOwnTpNr = { status().isNotFound }
            return Stream.of(
                    Arguments.of(orgNrA, tpNrA, ownTpNr.invoke()),
                    Arguments.of(orgNrB, tpNrB, ownTpNr.invoke()),
                    Arguments.of(orgNrB, tpNrA, doesntOwnTpNr.invoke()),
                    Arguments.of(orgNrA, tpNrB, doesntOwnTpNr.invoke()),
                    Arguments.of(orgNrA, invalidTpNr, doesntOwnTpNr.invoke()),
                    Arguments.of(invalidOrgNr, tpNrB, doesntOwnTpNr.invoke()),
                    Arguments.of(validVaultMappedOrgNrA, validVaultMappedTpNrA, ownTpNr.invoke()),
                    Arguments.of(validVaultMappedOrgNrB, validVaultMappedTpNrB, ownTpNr.invoke()),
                    Arguments.of(validVaultMappedOrgNrA, validVaultMappedTpNrB, doesntOwnTpNr.invoke()),
                    Arguments.of(validVaultMappedOrgNrB, validVaultMappedTpNrA, doesntOwnTpNr.invoke()),
                    Arguments.of(validVaultMappedOrgNrA, invalidTpNr, doesntOwnTpNr.invoke()),
                    Arguments.of(invalidOrgNr, validVaultMappedTpNrA, doesntOwnTpNr.invoke()))
        }
    }

    @ParameterizedTest
    @ArgumentsSource(TestData::class)
    fun testTpNrTilhorendeOrganisasjonHead(orgNr: String, tpNr: String, expectedResult: ResultMatcher) {
        mockMvc.perform(
                head("/organisation/")
                        .header("orgnr", orgNr)
                        .header("tpnr", tpNr))
                .andExpect(expectedResult)
    }

    @ParameterizedTest
    @ArgumentsSource(TestData::class)
    fun testTpNrTilhorendeOrganisasjonGet(orgNr: String, tpNr: String, expectedResult: ResultMatcher) {
        mockMvc.perform(
                get("/organisation/")
                        .header("orgnr", orgNr)
                        .header("tpnr", tpNr))
                .andExpect(expectedResult)
    }
}