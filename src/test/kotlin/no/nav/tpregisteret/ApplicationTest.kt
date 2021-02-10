package no.nav.tpregisteret

import no.nav.pensjonsamhandling.maskinporten.validation.test.AutoConfigureMaskinportenValidator
import no.nav.pensjonsamhandling.maskinporten.validation.test.MaskinportenValidatorTestBuilder
import no.nav.tpregisteret.controller.OrganisationControllerTest
import no.nav.tpregisteret.controller.PersonControllerTest
import no.nav.tpregisteret.controller.YtelseControllerTest
import no.nav.tpregisteret.support.TestData
import no.nav.tpregisteret.support.TestData.PERSON_3
import no.nav.tpregisteret.support.TestData.YTELSE_1
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
@AutoConfigureMockMvc
@AutoConfigureWebMvc
@AutoConfigureDataJpa
@AutoConfigureMaskinportenValidator
internal class ApplicationTest {

    @Autowired
    private lateinit var maskinportenValidatorTestBuilder: MaskinportenValidatorTestBuilder

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `Organisation returns 204 on valid TpNr for OrgNr`() {
        mockMvc.get(OrganisationControllerTest.root) {
            headers {
                this["orgNr"] = TestData.TP_ORDNING_1.orgNr
                this["tpId"] = TestData.TP_ORDNING_1.tpId
            }
        }.andExpect { status { isNoContent } }
    }

    @Test
    fun `Organisation returns 204 on valid vault TpNr for OrgNr`() {
        mockMvc.get(OrganisationControllerTest.root) {
            headers {
                this["orgNr"] = TestData.VAULT_TP_ORDNING_1.orgNr
                this["tpId"] = TestData.VAULT_TP_ORDNING_1.tpId
            }
        }.andExpect { status { isNoContent } }
    }

    @Test
    fun `Organisation OrgNr returns 200 on valid TSS ID`() {
        mockMvc.get(OrganisationControllerTest.orgNrUrl) {
            headers { this["tssId"] = TestData.TP_ORDNING_1.tssId }
        }.andExpect {
            status { isOk }
            content { string(TestData.TP_ORDNING_1.orgNr) }
        }
    }

    @Test
    fun `Organisation Name returns 200 on valid OrgNr`() {
        mockMvc.get(OrganisationControllerTest.navnUrl) {
            headers {
                this["orgNr"] = TestData.ORG_1.orgNr
            }
        }.andExpect {
            status { isOk }
            content { json(TestData.ORG_1.json) }
        }
    }

    @Test
    fun `Person TpOrdninger returns 200 on valid result`() {
        mockMvc.get(PersonControllerTest.tpordningerUrl) {
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
    fun `Person Forhold returns 200 on valid forhold`() {
        mockMvc.get(PersonControllerTest.forholdUrl) {
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
    fun `Person Ytelser returns 200 on correct results`() {
        mockMvc.get(PersonControllerTest.ytelserUrl) {
            headers {
                setBearerAuth(getToken(PERSON_3.tpForhold.first().orgNr))
                this["fnr"] = PERSON_3.fnr
                this["tpId"] = PERSON_3.tpForhold.first().tpId
            }
        }.andExpect {
            status { isOk }
            content { json(TestData.TestYtelse.getJson(PERSON_3, PERSON_3.tpForhold.first())) }
        }
    }

    @Test
    fun `Ytelser returns 200 on valid ytelseId`() {
        mockMvc.get(YtelseControllerTest.root) {
            headers {
                setBearerAuth(getToken(YTELSE_1.tpOrdning.orgNr))
                this["ytelseId"] = YTELSE_1.id.toString()
            }
        }.andExpect {
            status { isOk }
            content { json(YTELSE_1.json) }
        }
    }

    fun getToken(orgno: String): String =
        maskinportenValidatorTestBuilder.generateToken(TPREGISTERET_SCOPE, orgno).serialize()
}