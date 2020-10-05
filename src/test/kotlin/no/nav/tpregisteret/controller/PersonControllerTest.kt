package no.nav.tpregisteret.controller

import no.nav.security.token.support.spring.api.EnableJwtTokenValidation
import no.nav.security.token.support.test.spring.TokenGeneratorConfiguration
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@EnableJwtTokenValidation
@Import(TokenGeneratorConfiguration::class)
class PersonControllerTest {

    private companion object : Tokenizer() {
        const val root = "/person"
        const val tpordningerUrl = "$root/tpordninger"
        const val ytelserUrl = "$root/ytelser"
        const val forholdUrl = "$root/forhold"
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `TpOrdninger returns 200 on empty result`() {
        mockMvc.get(tpordningerUrl) {
            headers {
                this[auth] = bearer
                this["fnr"] = PERSON_1.fnr
            }
        }.andExpect {
            status { isOk }
            content { json(PERSON_1.json) }
        }
    }

    @Test
    fun `TpOrdninger returns 200 on single result`() {
        mockMvc.get(tpordningerUrl) {
            headers {
                this[auth] = bearer
                this["fnr"] = PERSON_2.fnr
            }
        }.andExpect {
            status { isOk }
            content { json(PERSON_2.json) }
        }
    }

    @Test
    fun `TpOrdninger returns 200 on multiple results`() {
        mockMvc.get(tpordningerUrl) {
            headers {
                this[auth] = bearer
                this["fnr"] = PERSON_3.fnr
            }
        }.andExpect {
            status { isOk }
            content { json(PERSON_3.json) }
        }
    }

    @Test
    fun `TpOrdninger returns 404 on invalid fnr`() {
        mockMvc.get(tpordningerUrl) {
            headers {
                this[auth] = bearer
                this["fnr"] = "12345678910"
            }
        }.andExpect {
            status { isNotFound }
        }
    }

    @Test
    fun `TpOrdninger returns 401 on missing token`() {
        mockMvc.get(tpordningerUrl) {
            headers {
                this["fnr"] = PERSON_1.fnr
            }
        }.andExpect {
            status { isUnauthorized }
        }
    }

    @Test
    fun `Forhold returns 200 on valid forhold`() {
        mockMvc.get(forholdUrl) {
            headers {
                this[auth] = bearer
                this["fnr"] = PERSON_3.fnr
                this["tpId"] = PERSON_3.tpForhold.first().tpId
            }
        }.andExpect {
            status { isOk }
        }
    }

    @Test
    fun `Forhold returns 404 on invalid forhold`() {
        mockMvc.get(forholdUrl) {
            headers {
                this[auth] = bearer
                this["fnr"] = PERSON_1.fnr
                this["tpId"] = TP_ORDNING_1.tpId
            }
        }.andExpect {
            status { isNotFound }
        }
    }

    @Test
    fun `Ytelser returns 200 on empty result`() {
        mockMvc.get(ytelserUrl) {
            headers {
                this[auth] = bearer
                this["fnr"] = PERSON_5.fnr
                this["tpId"] = PERSON_5.tpForhold.first().tpId
            }
        }.andExpect {
            status { isOk }
            content { json(TestYtelse.getJson(PERSON_5, PERSON_5.tpForhold.first())) }
        }
    }

    @Test
    fun `Ytelser returns 200 on correct results`() {
        mockMvc.get(ytelserUrl) {
            headers {
                this[auth] = bearer
                this["fnr"] = PERSON_3.fnr
                this["tpId"] = PERSON_3.tpForhold.first().tpId
            }
        }.andExpect {
            status { isOk }
            content { json(TestYtelse.getJson(PERSON_3, PERSON_3.tpForhold.first())) }
        }
    }

    @Test
    fun `Ytelser returns 404 on invalid ordning for person`() {
        mockMvc.get(ytelserUrl) {
            headers {
                this[auth] = bearer
                this["fnr"] = PERSON_2.fnr
                this["tpId"] = PERSON_7.tpForhold.first().tpId
            }
        }.andExpect {
            status { isNotFound }
        }
    }
}