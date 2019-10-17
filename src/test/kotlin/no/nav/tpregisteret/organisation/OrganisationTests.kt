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
            val validOrgNrA = "000000000"
            val validOrgNrB = "111111111"
            val validVaultOrgNr = "222222222"
            val invalidOrgNr = "000000001"
            val validTpNrA = "1111"
            val validTpNrB = "4444"
            val invalidTpNr = "7777"
            val ownTpNr = { status().isOk }
            val doesntOwnTpNr = { status().isNotFound }
            return Stream.of(Arguments.of(validOrgNrA, validTpNrA, ownTpNr.invoke()),
                    Arguments.of(validOrgNrA, invalidTpNr, doesntOwnTpNr.invoke()),
                    Arguments.of(validOrgNrA, validTpNrB, doesntOwnTpNr.invoke()),
                    Arguments.of(validOrgNrB, validTpNrB, ownTpNr.invoke()),
                    Arguments.of(validOrgNrB, validTpNrA, doesntOwnTpNr.invoke()),
                    Arguments.of(validOrgNrB, invalidTpNr, doesntOwnTpNr.invoke()),
                    Arguments.of(invalidOrgNr, validTpNrA, doesntOwnTpNr.invoke()),
                    Arguments.of(invalidOrgNr, validTpNrB, doesntOwnTpNr.invoke()),
                    Arguments.of(invalidOrgNr, invalidTpNr, doesntOwnTpNr.invoke()),
                    Arguments.of(validVaultOrgNr, validTpNrA, ownTpNr.invoke()),
                    Arguments.of(validVaultOrgNr, validTpNrB, doesntOwnTpNr.invoke()))
        }
    }

    @ParameterizedTest
    @ArgumentsSource(TestData::class)
    fun testTpNrTilhorendeOrganisasjonHead(orgNr: String, tpNr: String, expectedResult: ResultMatcher) {
        mockMvc.perform(head("/organisation/$orgNr/tpnr/$tpNr"))
                .andExpect(expectedResult)
    }

    @ParameterizedTest
    @ArgumentsSource(TestData::class)
    fun testTpNrTilhorendeOrganisasjonGet(orgNr: String, tpNr: String, expectedResult: ResultMatcher) {
        mockMvc.perform(get("/organisation/$orgNr/tpnr/$tpNr"))
                .andExpect(expectedResult)
    }
}