package no.nav.tpregisteret.controller

import no.nav.pensjonsamhandling.maskinporten.validation.test.AutoConfigureMaskinportenValidator
import no.nav.pensjonsamhandling.maskinporten.validation.test.MaskinportenValidatorTestBuilder
import no.nav.tpregisteret.TPREGISTERET_SCOPE
import no.nav.tpregisteret.support.TestData.YTELSE_1
import no.nav.tpregisteret.support.TestData.YTELSE_2
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@AutoConfigureMaskinportenValidator
class YtelseControllerTest {

    internal companion object {
        const val root = "/ytelse"
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var maskinportenValidatorTestBuilder: MaskinportenValidatorTestBuilder

    @Test
    fun `Ytelser returns 200 on valid ytelseId`() {
        mockMvc.get(root) {
            headers {
                setBearerAuth(getToken(YTELSE_1.tpOrdning.orgNr))
                this["ytelseId"] = YTELSE_1.id.toString()
            }
        }.andExpect {
            status { isOk }
            content { json(YTELSE_1.json) }
        }
    }

    @Test
    fun `Ytelser returns 403 if ytelse does not belong to authorized org`() {
        mockMvc.get(root) {
            headers {
                setBearerAuth(getToken(YTELSE_2.tpOrdning.orgNr))
                this["ytelseId"] = "123123123"
            }
        }.andExpect {
            status { isForbidden }
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

    fun getToken(orgno: String): String =
        maskinportenValidatorTestBuilder.generateToken(TPREGISTERET_SCOPE, orgno).serialize()
}