package no.nav.tpregisteret.controller

import no.nav.tpregisteret.support.ImportTpregisteretBeans
import no.nav.tpregisteret.support.TestData.PERSON_1
import no.nav.tpregisteret.support.TestData.PERSON_2
import no.nav.tpregisteret.support.TestData.PERSON_3
import no.nav.tpregisteret.support.TestData.PERSON_5
import no.nav.tpregisteret.support.TestData.PERSON_7
import no.nav.tpregisteret.support.TestData.TP_ORDNING_1
import no.nav.tpregisteret.support.TestData.TestYtelse
import no.nav.tpregisteret.support.Tokenizer
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

    private companion object : Tokenizer {
        const val root = "/person"
        const val tpordningerUrl = "$root/tpordninger"
        const val ytelserUrl = "$root/ytelser"
        const val forholdUrl = "$root/forhold"
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `TpOrdninger returns 200 on empty result`() {
        mockMvc.perform(get(tpordningerUrl)
                .header(auth, bearer)
                .header("fnr", PERSON_1.fnr))
                .andExpect(status().isOk)
                .andExpect(content().json(PERSON_1.json))
    }

    @Test
    fun `TpOrdninger returns 200 on single result`() {
        mockMvc.perform(get(tpordningerUrl)
                .header(auth, bearer)
                .header("fnr", PERSON_2.fnr))
                .andExpect(status().isOk)
                .andExpect(
                        content().json(PERSON_2.json))
    }

    @Test
    fun `TpOrdninger returns 200 on multiple results`() {
        mockMvc.perform(get(tpordningerUrl)
                .header(auth, bearer)
                .header("fnr", PERSON_3.fnr))
                .andExpect(status().isOk)
                .andExpect(content().json(PERSON_3.json))
    }

    @Test
    fun `TpOrdninger returns 404 on invalid fnr`() {
        mockMvc.perform(get(tpordningerUrl)
                .header(auth, bearer)
                .header("fnr", "12345678910"))
                .andExpect(status().isNotFound)
    }

    @Test
    fun `TpOrdninger returns 401 on missing token`() {
        mockMvc.perform(get(tpordningerUrl)
                .header("fnr", PERSON_1.fnr))
                .andExpect(status().isUnauthorized)
    }

    @Test
    fun `Forhold returns 200 on valid forhold`() {
        mockMvc.perform(
                get(forholdUrl)
                        .header(auth, bearer)
                        .header("fnr", PERSON_3.fnr)
                        .header("tpId", PERSON_3.tpForhold.first().tpId)
        )
                .andExpect(status().isOk)
    }

    @Test
    fun `Forhold returns 404 on invalid forhold`() {
        mockMvc.perform(
                get(forholdUrl)
                        .header(auth, bearer)
                        .header("fnr", PERSON_1.fnr)
                        .header("tpId", TP_ORDNING_1.tpId)
        ).andExpect(status().isNotFound)
    }

    @Test
    fun `Ytelser returns 200 on empty result`() {
        mockMvc.perform(
                get(ytelserUrl)
                        .header(auth, bearer)
                        .header("fnr", PERSON_5.fnr)
                        .header("tpId", PERSON_5.tpForhold.first().tpId)
        )
                .andExpect(status().isOk)
                .andExpect(
                        content().json(TestYtelse.getJson(PERSON_5, PERSON_5.tpForhold.first()))
                )
    }

    @Test
    fun `Ytelser returns 200 on correct results`() {
        mockMvc.perform(
                get(ytelserUrl)
                        .header(auth, bearer)
                        .header("fnr", PERSON_3.fnr)
                        .header("tpId", PERSON_3.tpForhold.first().tpId)
        )
                .andExpect(status().isOk)
                .andExpect(content().json(TestYtelse.getJson(PERSON_3, PERSON_3.tpForhold.first())))
    }

    @Test
    fun `Ytelser returns 404 on invalid ordning for person`() {
        mockMvc.perform(
                get(ytelserUrl)
                        .header(auth, bearer)
                        .header("fnr", PERSON_2.fnr)
                        .header("tpId", PERSON_7.tpForhold.first().tpId)
        )
                .andExpect(status().isNotFound)
    }
}