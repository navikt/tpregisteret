package no.nav.tpregisteret.controller

import no.nav.tpregisteret.support.ImportTpregisteretBeans
import no.nav.tpregisteret.support.TestData.ORG_1
import no.nav.tpregisteret.support.TestData.ORG_2
import no.nav.tpregisteret.support.TestData.TP_ORDNING_1
import no.nav.tpregisteret.support.TestData.VAULT_TP_ORDNING_1
import no.nav.tpregisteret.support.Tokenizer
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.head

@WebMvcTest
@AutoConfigureDataJpa
@ImportTpregisteretBeans
class OrganisationControllerTest {

    private companion object : Tokenizer {
        const val root = "/organisation"
        const val orgNrUrl = "$root/orgnr"
        const val navnUrl = "$root/navn"
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `Check returns 204 on valid TpNr for OrgNr`() {
        mockMvc.get(root) {
            headers {
                this["orgNr"] = TP_ORDNING_1.orgNr
                this["tpId"] = TP_ORDNING_1.tpId
            }
        }.andExpect { status { isNoContent } }
    }

    @Test
    fun `Check returns 204 on valid vault TpNr for OrgNr`() {
        mockMvc.get(root) {
            headers {
                this["orgNr"] = VAULT_TP_ORDNING_1.orgNr
                this["tpId"] = VAULT_TP_ORDNING_1.tpId
            }
        }.andExpect { status { isNoContent } }
    }

    @Test
    fun `Check returns 404 on invalid TpNr for OrgNr`() {
        mockMvc.head(root) {
            headers {
                this["orgNr"] = ORG_1.orgNr
                this["tpId"] = ORG_2.tpOrdninger.first().tpId
            }
        }.andExpect {
            status { isNotFound }
        }
    }

    @Test
    fun `OrgNr returns 200 on valid TSS ID`() {
        mockMvc.get(orgNrUrl) {
            headers { this[auth] = bearer }
            headers { this["tssId"] = TP_ORDNING_1.tssId }
        }.andExpect {
            status { isOk }
            content { string(TP_ORDNING_1.orgNr) }
        }
    }

    @Test
    fun `OrgNr returns 404 on invalid TSS ID`() {
        mockMvc.get(orgNrUrl) {
            headers {
                this[auth] = bearer
                this["tssId"] = "12345678910"
            }
        }.andExpect {
            status { isNotFound }
        }
    }

    @Test
    fun `Name returns 200 on valid OrgNr`() {
        mockMvc.get(navnUrl) {
            headers {
                this[auth] = bearer
                this["orgNr"] = ORG_1.orgNr
            }
        }.andExpect {
            status { isOk }
            content { json(ORG_1.json) }
        }
    }

    @Test
    fun `Name returns 404 on invalid OrgNr`() {
        mockMvc.get(navnUrl) {
            headers {
                this[auth] = bearer
                this["orgNr"] = "123456789"
            }
        }.andExpect {
            status { isNotFound }
        }
    }
}