package no.nav.tpregisteret.organisation

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head

@SpringBootTest
@AutoConfigureMockMvc
class OrganisationTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

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