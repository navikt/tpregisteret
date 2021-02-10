package no.nav.tpregisteret.controller

import no.nav.pensjonsamhandling.maskinporten.validation.test.AutoConfigureMaskinportenValidator
import no.nav.pensjonsamhandling.maskinporten.validation.test.MaskinportenValidatorTestBuilder
import no.nav.tpregisteret.TPREGISTERET_SCOPE
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@AutoConfigureWebMvc
@AutoConfigureDataJpa
@AutoConfigureMaskinportenValidator
internal class PersonControllerTest {

    internal companion object {
        private const val root = "/person"
        const val tpordningerUrl = "$root/tpordninger"
        const val ytelserUrl = "$root/ytelser"
        const val forholdUrl = "$root/forhold"
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var maskinportenValidatorTestBuilder: MaskinportenValidatorTestBuilder

    @Test
    fun `TpOrdninger returns 403 on no forhold with authorized org`() {
        mockMvc.get(tpordningerUrl) {
            headers {
                setBearerAuth(getToken(PERSON_3.tpForhold.first().orgNr))
                this["fnr"] = PERSON_1.fnr
            }
        }.andExpect {
            status { isForbidden }
        }
    }

    @Test
    fun `TpOrdninger returns 200 on single result`() {
        mockMvc.get(tpordningerUrl) {
            headers {
                setBearerAuth(getToken(PERSON_2.tpForhold.first().orgNr))
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
                setBearerAuth(getToken(PERSON_3.tpForhold.first().orgNr))
                this["fnr"] = PERSON_3.fnr
            }
        }.andExpect {
            status { isOk }
            content { json(PERSON_3.json) }
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
                setBearerAuth(getToken(PERSON_3.tpForhold.first().orgNr))
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
                setBearerAuth(getToken(TP_ORDNING_1.orgNr))
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
                setBearerAuth(getToken(PERSON_5.tpForhold.first().orgNr))
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
                setBearerAuth(getToken(PERSON_3.tpForhold.first().orgNr))
                this["fnr"] = PERSON_3.fnr
                this["tpId"] = PERSON_3.tpForhold.first().tpId
            }
        }.andExpect {
            status { isOk }
            content { json(TestYtelse.getJson(PERSON_3, PERSON_3.tpForhold.first())) }
        }
    }

    @Test
    fun `Ytelser filters results`() {
        mockMvc.get(ytelserUrl) {
            headers {
                setBearerAuth(getToken(PERSON_2.tpForhold.first().orgNr))
                this["fnr"] = PERSON_2.fnr
                this["tpId"] = PERSON_2.tpForhold.first().tpId
            }
        }.andExpect {
            status { isOk }
            content { json(TestYtelse.getJson(PERSON_2, PERSON_2.tpForhold.first())) }
        }
    }

    @Test
    fun `Ytelser returns 404 on invalid ordning for person`() {
        mockMvc.get(ytelserUrl) {
            headers {
                setBearerAuth(getToken(PERSON_7.tpForhold.first().orgNr))
                this["fnr"] = PERSON_2.fnr
                this["tpId"] = PERSON_7.tpForhold.first().tpId
            }
        }.andExpect {
            status { isNotFound }
        }
    }

    fun getToken(orgno: String): String =
        maskinportenValidatorTestBuilder.generateToken(TPREGISTERET_SCOPE, orgno).serialize()
}