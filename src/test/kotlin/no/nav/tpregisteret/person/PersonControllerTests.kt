package no.nav.tpregisteret.person

import no.nav.tpregisteret.TestPerson.Companion.testPerson1
import no.nav.tpregisteret.TestPerson.Companion.testPerson2
import no.nav.tpregisteret.TestPerson.Companion.testPerson3
import no.nav.tpregisteret.TestSecurityConfig
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig::class)
class PersonControllerTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun valid_parameter_returns_200_and_empty_result() {
        mockMvc.perform(get("/person/tpordninger").header("fnr", testPerson1.fnr))
                .andExpect(status().isOk)
                .andExpect(MockMvcResultMatchers.content().json("[]"))
    }

    @Test
    fun valid_parameter_returns_200_and_single_result() {
        mockMvc.perform(get("/person/tpordninger").header("fnr", testPerson2.fnr))
                .andExpect(status().isOk)
                .andExpect(MockMvcResultMatchers.content().json("[{\"tssId\":\"11111111111\",\"tpId\":\"1111\",\"orgNr\":\"000000000\",\"navn\":\"TP1\"}]"))
    }

    @Test
    fun valid_parameter_returns_200_and_results() {
        mockMvc.perform(get("/person/tpordninger").header("fnr", testPerson3.fnr))
                .andExpect(status().isOk)
                .andExpect(MockMvcResultMatchers.content().json("[{\"tssId\":\"11111111111\",\"tpId\":\"1111\",\"orgNr\":\"000000000\",\"navn\":\"TP1\"},{\"tssId\":\"22222222222\",\"tpId\":\"2222\",\"orgNr\":\"000000000\",\"navn\":\"TP2\"}]"))
    }

    @Test
    fun root_returns_404() {
        mockMvc.perform(get("/"))
                .andExpect(status().isNotFound)
    }
}