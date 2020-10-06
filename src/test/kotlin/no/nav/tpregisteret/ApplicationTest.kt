package no.nav.tpregisteret

import no.nav.security.token.support.spring.api.EnableJwtTokenValidation
import no.nav.security.token.support.test.spring.TokenGeneratorConfiguration
import no.nav.tpregisteret.controller.OrganisationControllerTest
import no.nav.tpregisteret.controller.PersonControllerTest
import no.nav.tpregisteret.controller.YtelseControllerTest
import no.nav.tpregisteret.support.TestData
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
internal class ApplicationTest {

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
                this[PersonControllerTest.auth] = PersonControllerTest.bearer
                this["fnr"] = TestData.PERSON_3.fnr
            }
        }.andExpect {
            status { isOk }
            content { json(TestData.PERSON_3.json) }
        }
    }

    @Test
    fun `Person Forhold returns 200 on valid forhold`() {
        mockMvc.get(PersonControllerTest.forholdUrl) {
            headers {
                this[PersonControllerTest.auth] = PersonControllerTest.bearer
                this["fnr"] = TestData.PERSON_3.fnr
                this["tpId"] = TestData.PERSON_3.tpForhold.first().tpId
            }
        }.andExpect {
            status { isOk }
        }
    }

    @Test
    fun `Person Ytelser returns 200 on correct results`() {
        mockMvc.get(PersonControllerTest.ytelserUrl) {
            headers {
                this[PersonControllerTest.auth] = PersonControllerTest.bearer
                this["fnr"] = TestData.PERSON_3.fnr
                this["tpId"] = TestData.PERSON_3.tpForhold.first().tpId
            }
        }.andExpect {
            status { isOk }
            content { json(TestData.TestYtelse.getJson(TestData.PERSON_3, TestData.PERSON_3.tpForhold.first())) }
        }
    }

    @Test
    fun `Ytelser returns 200 on valid ytelseId`() {
        mockMvc.get(YtelseControllerTest.root) {
            headers {
                this[YtelseControllerTest.auth] = YtelseControllerTest.bearer
                this["ytelseId"] = TestData.YTELSE_1.id.toString()
            }
        }.andExpect {
            status { isOk }
            content { json(TestData.YTELSE_1.json) }
        }
    }
}