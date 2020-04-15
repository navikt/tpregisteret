package no.nav.tpregisteret.nais

import no.nav.tpregisteret.TestSecurityConfig
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig::class)
class NaisReadinessTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun liveness_and_actuator_permitted() {
        mockMvc.perform(get("/actuator/prometheus")).andExpect(status().isOk)
        mockMvc.perform(get("/isAlive")).andExpect(status().isOk)
        mockMvc.perform(get("/isReady")).andExpect(status().isOk)
    }
}
