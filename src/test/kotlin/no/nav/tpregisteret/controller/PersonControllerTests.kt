package no.nav.tpregisteret.controller

import com.fasterxml.jackson.databind.ObjectMapper
import no.nav.tpregisteret.ImportTpregisteretBeans
import no.nav.tpregisteret.TestPerson.Companion.YTELSE_DTO_FOR_TEST_PERSON3_AND_TP_ORDNING_1
import no.nav.tpregisteret.TestPerson.Companion.testPerson1
import no.nav.tpregisteret.TestPerson.Companion.testPerson2
import no.nav.tpregisteret.TestPerson.Companion.testPerson3
import no.nav.tpregisteret.TestPerson.Companion.testPerson5
import no.nav.tpregisteret.TestPerson.Companion.testPerson7
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
class PersonControllerTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `Tpordninger returns 200 and empty result`() {
        mockMvc.perform(get("/person/tpordninger").header("fnr", testPerson1.fnr))
                .andExpect(status().isOk)
                .andExpect(content().json("[]"))
    }

    @Test
    fun `Tpordninger returns 200 and correct single result`() {
        mockMvc.perform(get("/person/tpordninger").header("fnr", testPerson2.fnr))
                .andExpect(status().isOk)
                .andExpect(
                        content().json("""[{"id":"11111111111","tpNr":"1111","orgNr":"000000000","navn":"TP1"}]"""))
    }

    @Test
    fun `Tpordninger returns 200 with correct results`() {
        mockMvc.perform(get("/person/tpordninger").header("fnr", testPerson3.fnr))
                .andExpect(status().isOk)
                .andExpect(content().json("""[{"id":"11111111111","tpNr":"1111","orgNr":"000000000","navn":"TP1"},{"id":"22222222222","tpNr":"2222","orgNr":"000000000","navn":"TP2"}]"""))
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
                        .header("fnr", testPerson1.fnr)
                        .header("tpId", testPerson3.tpForhold.first().tpId)
        ).andExpect(status().isNotFound)
    }

    @Test
    fun `Forhold returns 200 with correct result`() {
        mockMvc.perform(
                get("/person/forhold")
                        .header("fnr", testPerson3.fnr)
                        .header("tpId", testPerson3.tpForhold.first().tpId)
        )
                .andExpect(status().isOk)
    }

    @Test
    fun `Ytelser returns 404 for invalid person-ordning combination`() {
        mockMvc.perform(
                get("/person/ytelser")
                        .header("fnr", testPerson2.fnr)
                        .header("tpId", testPerson7.tpForhold.first().tpId)
        )
                .andExpect(status().isNotFound)
    }

    @Test
    fun `Ytelser returns 200 with empty result`() {
        mockMvc.perform(
                get("/person/ytelser")
                        .header("fnr", testPerson5.fnr)
                        .header("tpId", testPerson5.tpForhold.first().tpId)
        )
                .andExpect(status().isOk)
                .andExpect(
                        content().json("[]")
                )
    }

    @Test
    fun `Ytelser returns 200 with correct results`() {
        mockMvc.perform(
                get("/person/ytelser")
                        .header("fnr", testPerson3.fnr)
                        .header("tpId", testPerson3.tpForhold.first().tpId)
        )
                .andExpect(status().isOk)
                .andExpect(content().json(YTELSE_DTO_FOR_TEST_PERSON3_AND_TP_ORDNING_1))
    }
}