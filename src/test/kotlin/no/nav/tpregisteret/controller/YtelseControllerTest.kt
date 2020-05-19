package no.nav.tpregisteret.controller

import no.nav.tpregisteret.support.ImportTpregisteretBeans
import no.nav.tpregisteret.support.TestData.YTELSE_1
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
internal class YtelseControllerTest {

    private companion object : Tokenizer {
        const val root = "/ytelse"
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `Ytelser returns 200 on valid ytelseId`() {
        mockMvc.perform(get(root)
                .header(auth, bearer)
                .header("ytelseId", YTELSE_1.id))
                .andExpect(status().isOk)
                .andExpect(content().json(YTELSE_1.json))
    }

    @Test
    fun `Ytelser returns 404 on invalid ytelseId`() {
        mockMvc.perform(get(root)
                .header(auth, bearer)
                .header("ytelseId", "123123123"))
                .andExpect(status().isNotFound)
    }

    @Test
    fun `Ytelser returns 401 on missing token`() {
        mockMvc.perform(get(root)
                .header("ytelseId", YTELSE_1.id))
                .andExpect(status().isUnauthorized)
    }
}