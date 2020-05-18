package no.nav.tpregisteret.controller

import no.nav.tpregisteret.support.ImportTpregisteretBeans
import no.nav.tpregisteret.support.TestData.PERSON_1
import no.nav.tpregisteret.support.TestData.PERSON_2
import no.nav.tpregisteret.support.TestData.PERSON_3
import no.nav.tpregisteret.support.TestData.PERSON_5
import no.nav.tpregisteret.support.TestData.PERSON_7
import no.nav.tpregisteret.support.TestData.TP_ORDNING_1
import no.nav.tpregisteret.support.TestData.TestYtelse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
@AutoConfigureDataJpa
@ImportTpregisteretBeans
class PersonControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `TpOrdninger returns 200 and empty result`() {
        mockMvc.perform(get("/person/tpordninger").header("fnr", PERSON_1.fnr))
                .andExpect(status().isOk)
                .andExpect(content().json(PERSON_1.json))
    }

    @Test
    fun `TpOrdninger returns 200 and correct single result`() {
        mockMvc.perform(get("/person/tpordninger").header("fnr", PERSON_2.fnr))
                .andExpect(status().isOk)
                .andExpect(
                        content().json(PERSON_2.json))
    }

    @Test
    fun `TpOrdninger returns 200 with correct results`() {
        mockMvc.perform(get("/person/tpordninger").header("fnr", PERSON_3.fnr))
                .andExpect(status().isOk)
                .andExpect(content().json(PERSON_3.json))
    }

    @Test
    fun `TpOrdninger returns 404 when missing person`() {
        mockMvc.perform(get("/person/tpordninger").header("fnr", "12345678910"))
                .andExpect(status().isNotFound)
    }

    @Test
    fun `Root returns 404`() {
        mockMvc.perform(get("/"))
                .andExpect(status().isNotFound)
    }

    @Test
    fun `Forhold returns 404 when not found`() {
        mockMvc.perform(
                get("/person/forhold")
                        .header("fnr", PERSON_1.fnr)
                        .header("tpId", TP_ORDNING_1.tpId)
        ).andExpect(status().isNotFound)
    }

    @Test
    fun `Forhold returns 200 with correct result`() {
        mockMvc.perform(
                get("/person/forhold")
                        .header("fnr", PERSON_3.fnr)
                        .header("tpId", PERSON_3.tpForhold.first().tpId)
        )
                .andExpect(status().isOk)
    }

    @Test
    fun `Ytelser returns 404 for invalid person-ordning combination`() {
        mockMvc.perform(
                get("/person/ytelser")
                        .header("fnr", PERSON_2.fnr)
                        .header("tpId", PERSON_7.tpForhold.first().tpId)
        )
                .andExpect(status().isNotFound)
    }

    @Test
    fun `Ytelser returns 200 with empty result`() {
        mockMvc.perform(
                get("/person/ytelser")
                        .header("fnr", PERSON_5.fnr)
                        .header("tpId", PERSON_5.tpForhold.first().tpId)
        )
                .andExpect(status().isOk)
                .andExpect(
                        content().json(TestYtelse.getJson(PERSON_5, PERSON_5.tpForhold.first()))
                )
    }

    @Test
    fun `Ytelser returns 200 with correct results`() {
        mockMvc.perform(
                get("/person/ytelser")
                        .header("fnr", PERSON_3.fnr)
                        .header("tpId", PERSON_3.tpForhold.first().tpId)
        )
                .andExpect(status().isOk)
                .andExpect(content().json(TestYtelse.getJson(PERSON_3, PERSON_3.tpForhold.first())))
    }
}