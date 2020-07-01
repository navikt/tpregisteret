package no.nav.tpregisteret.controller

import no.nav.tpregisteret.support.ImportTpregisteretBeans
import no.nav.tpregisteret.support.TestData.YTELSE_1
import no.nav.tpregisteret.support.Tokenizer
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

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
        mockMvc.get(root) {
            headers {
                this[auth] = bearer
                this["ytelseId"] = YTELSE_1.id.toString()
            }
        }.andExpect {
            status { isOk }
            content { json(YTELSE_1.json) }
        }
    }

    @Test
    fun `Ytelser returns 404 on invalid ytelseId`() {
        mockMvc.get(root) {
            headers {
                this[auth] = bearer
                this["ytelseId"] = "123123123"
            }
        }.andExpect {
            status { isNotFound }
        }
    }

    @Test
    fun `Ytelser returns 401 on missing token`() {
        mockMvc.get(root) {
            headers {
                this["ytelseId"] = YTELSE_1.id.toString()
            }
        }.andExpect {
            status { isUnauthorized }
        }
    }
}