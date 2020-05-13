package no.nav.tpregisteret.nais

import no.nav.tpregisteret.ImportTpregisteretBeans
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
@AutoConfigureDataJpa
@ImportTpregisteretBeans
class NaisReadinessTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun liveness_and_actuator_permitted() {
        mockMvc.perform(get("/isAlive")).andExpect(status().isOk)
        mockMvc.perform(get("/isReady")).andExpect(status().isOk)
        mockMvc.perform(get("/actuator/prometheus")).andExpect(status().isOk)
    }
}
