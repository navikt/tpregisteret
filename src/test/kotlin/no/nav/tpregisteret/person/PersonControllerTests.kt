package no.nav.tpregisteret.person

import com.fasterxml.jackson.databind.ObjectMapper
import no.nav.tpregisteret.TestPerson.Companion.testPerson1
import no.nav.tpregisteret.TestPerson.Companion.testPerson2
import no.nav.tpregisteret.TestPerson.Companion.testPerson3
import no.nav.tpregisteret.TestPerson.Companion.testPerson4
import no.nav.tpregisteret.TestPerson.Companion.testPerson7
import no.nav.tpregisteret.TestSecurityConfig
import no.nav.tpregisteret.controller.PersonController
import no.nav.tpregisteret.dto.YtelseDto
import no.nav.tpregisteret.service.PersonService
import org.codehaus.jackson.type.TypeReference
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate
import kotlin.test.assertEquals

@WebMvcTest
@AutoConfigureDataJpa
@Import(TestSecurityConfig::class, PersonController::class, PersonService::class)
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
                .andExpect(content().json("""[{"tssId":"11111111111","tpId":"1111","orgNr":"000000000","navn":"TP1"}]"""))
    }

    @Test
    fun `Tpordninger returns 200 with correct results`() {
        mockMvc.perform(get("/person/tpordninger").header("fnr", testPerson3.fnr))
                .andExpect(status().isOk)
                .andExpect(content().json("""[{"tssId":"11111111111","tpId":"1111","orgNr":"000000000","navn":"TP1"},{"tssId":"22222222222","tpId":"2222","orgNr":"000000000","navn":"TP2"}]"""))
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
                        .header("fnr", testPerson4.fnr)
                        .header("tpId", testPerson4.tpForhold.first().tpId)
        )
                .andExpect(status().isOk)
                .andExpect(content().json("[]"))
    }

    @Test
    fun `Ytelser returns 200 with correct results`() {
        val listOfYtelserDto = listOf(
                YtelseDto(
                        1,
                        "00000000003",
                        LocalDate.of(2001, 1, 1),
                        null
                ),
                YtelseDto(
                        2,
                        "00000000003",
                        LocalDate.of(2001, 1, 1),
                        null
                )
        )

        mockMvc.perform(
                get("/person/ytelser")
                        .header("fnr", testPerson3.fnr)
                        .header("tpId", testPerson3.tpForhold.first().tpId)
        )
                .andExpect(status().isOk)
                .andDo{
                    assertEquals(objectMapper.writeValueAsString(listOfYtelserDto), it.response.contentAsString)
                }
    }
}