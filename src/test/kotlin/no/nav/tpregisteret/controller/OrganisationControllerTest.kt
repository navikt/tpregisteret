package no.nav.tpregisteret.controller

import no.nav.tpregisteret.ImportTpregisteretBeans
import no.nav.tpregisteret.TestOrganisation.Companion.invalidOrgNr
import no.nav.tpregisteret.TestOrganisation.Companion.invalidTpNr
import no.nav.tpregisteret.TestOrganisation.Companion.invalidTssId
import no.nav.tpregisteret.TestOrganisation.Companion.validOrgNr
import no.nav.tpregisteret.TestOrganisation.Companion.validTpNr
import no.nav.tpregisteret.TestOrganisation.Companion.validTssId
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
@AutoConfigureDataJpa
@ImportTpregisteretBeans
class OrganisationControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `Get returns 200 on valid OrgNr and TpNr`(){
        mockMvc.perform(
                get("/organisation/")
                        .header("orgnr", validOrgNr)
                        .header("tpnr", validTpNr))
                .andExpect(status().isOk)
                .andExpect(content().json("""[{"id":"11111111111","tpNr":"1111","orgNr":"000000000","navn":"TP1"}]"""))
    }

    @Test
    fun `Get returns 404 on invalid OrgNr`(){
        mockMvc.perform(
                head("/organisation/")
                        .header("orgnr", invalidOrgNr)
                        .header("tpnr", validTpNr))
                .andExpect(status().isNotFound)
    }

    @Test
    fun `Get returns 404 on invalid TpNr`(){
        mockMvc.perform(
                head("/organisation/")
                        .header("orgnr", validOrgNr)
                        .header("tpnr", invalidTpNr))
                .andExpect(status().isNotFound)
    }

    @Test
    fun `OrgNr returns 200 on valid TSS ID`() {
        mockMvc.perform(
                get("/organisation/orgnr/")
                        .header("tssid", validTssId))
                .andExpect(status().isOk)
                .andExpect(content().string(validOrgNr))
    }

    @Test
    fun `OrgNr returns 404 on invalid TSS ID`() {
        mockMvc.perform(
                get("/organisation/orgnr/")
                        .header("tssid", invalidTssId))
                .andExpect(status().isNotFound)
    }

    @Test
    fun `Name returns 200 on valid OrgNr`() {
        mockMvc.perform(
                get("/organisation/navn")
                        .header("orgnr", validOrgNr))
                .andExpect(status().isOk)
                .andExpect(content().string("""["TP1","TP2","TP3"]"""))
    }

    @Test
    fun `Name returns 404 on invalid OrgNr`() {
        mockMvc.perform(
                get("/organisation/navn")
                        .header("orgnr", invalidOrgNr))
                .andExpect(status().isNotFound)
    }
}