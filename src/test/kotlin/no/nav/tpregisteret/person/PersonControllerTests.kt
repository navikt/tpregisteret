package no.nav.tpregisteret.person

import no.nav.tpregisteret.TestPerson.Companion.testPerson1
import no.nav.tpregisteret.TestPerson.Companion.testPerson2
import no.nav.tpregisteret.TestPerson.Companion.testPerson3
import no.nav.tpregisteret.TestPerson.Companion.testPerson4
import no.nav.tpregisteret.TestPerson.Companion.testPerson7
import no.nav.tpregisteret.TestSecurityConfig
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig::class)
class PersonControllerTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `Tpordninger parameter returns 200 and empty result`() {
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
                .andExpect(content().json("""{"forholdId":"2","fnr":"00000000003","fom":"2020-05-06","tom":null}"""))
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
        mockMvc.perform(
                get("/person/ytelser")
                        .header("fnr", testPerson3.fnr)
                        .header("tpId", testPerson3.tpForhold.first().tpId)
        )
                .andExpect(status().isOk)
                .andExpect(content().json("""[{"ytelseId":"1","fnr":"00000000003","fom":"2020-05-06","tom":null},{"ytelseId":"2","fnr":"00000000003","fom":"2020-05-06","tom":null}]"""))
    }
}