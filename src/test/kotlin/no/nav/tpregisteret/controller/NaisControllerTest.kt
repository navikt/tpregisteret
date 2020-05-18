package no.nav.tpregisteret.controller

import no.nav.tpregisteret.support.ImportTpregisteretBeans
import org.junit.jupiter.api.Disabled
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
class NaisControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `isAlive OK`() {
        mockMvc.perform(get("/isAlive")).andExpect(status().isOk)
    }

    @Test
    fun `isReady OK`() {
        mockMvc.perform(get("/isReady")).andExpect(status().isOk)
    }

    @Test
    @Disabled("Actuator ties to separate application during test. Not accessible through MockMvc.")
    fun `Prometheus OK`() {
        mockMvc.perform(get("/actuator/prometheus")).andExpect(status().isOk)
    }
}
