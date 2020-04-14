package no.nav.tpregisteret.organisation

import no.nav.tpregisteret.organisation.TestData.Companion.invalidTssId
import no.nav.tpregisteret.organisation.TestData.Companion.orgNrA
import no.nav.tpregisteret.organisation.TestData.Companion.tssIdA
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class OrganisationTSSIdTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `test TSSId returns valid OrgNr`() {

        mockMvc.perform(
                get("/organisation/orgnr/")
                        .header("tssid", tssIdA))
                .andExpect(status().isOk)
                .andExpect(content().string("000000000"))
    }

    @Test
    fun `test TSSId does not return invalid OrgNr`() {
        mockMvc.perform(
                get("/organisation/orgnr/")
                        .header("tssid", invalidTssId))
                .andExpect(status().isNotFound)
    }

    @Test
    fun `test get Name from OrgNr`() {
        mockMvc.perform(
                get("/organisation/navn")
                        .header("orgnr", orgNrA))
                .andExpect(status().isOk)
                .andExpect(content().string("TP1"))
    }

    @Test
    fun `test Name unknown orgnr returns 404`() {
        mockMvc.perform(
                get("/organisation/navn")
                        .header("orgnr", "12344321"))
                .andExpect(status().isNotFound)
    }
}