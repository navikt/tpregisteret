package no.nav.tpregisteret.controller

import no.nav.tpregisteret.support.ImportTpregisteretBeans
import no.nav.tpregisteret.support.TestData.YTELSE_1
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
internal class YtelseControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `Ytelser returns 200 and empty result`() {
        mockMvc.perform(get("/ytelse").header("ytelseId", YTELSE_1.id))
                .andExpect(status().isOk)
                .andExpect(content().json(YTELSE_1.json))
    }

    @Test
    fun `Ytelser returns 404 and empty result`() {
        mockMvc.perform(get("/ytelse").header("ytelseId", "123123123"))
                .andExpect(status().isNotFound)
    }
}