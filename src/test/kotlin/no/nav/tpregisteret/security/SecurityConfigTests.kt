package no.nav.tpregisteret.security

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc

import no.nav.tokentest.TokenClaims
import no.nav.tokentest.TokenHandler
import no.nav.tokentest.TokenHeaders
import no.nav.tpregisteret.TestPerson.Companion.testPerson1

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private lateinit var wireMockServer: WireMockServer

    @Autowired
    private lateinit var tokenHandler: TokenHandler

    @BeforeEach
    fun setup() {
        wireMockServer = WireMockServer(8090)
        wireMockServer.start()
        wireMockServer.stubFor(WireMock.get(urlEqualTo("/sts/jwks"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withStatus(200).withBody(tokenHandler.getJWKS("ignored"))))
    }

    @AfterEach
    fun teardown() = wireMockServer.stop()

    @Test
    fun liveness_and_actuator_permitted() {
        mockMvc.perform(get("/actuator/prometheus")).andExpect(status().isOk)
        mockMvc.perform(get("/isAlive")).andExpect(status().isOk)
        mockMvc.perform(get("/isReady")).andExpect(status().isOk)
    }

    @Test
    fun valid_token_authenticated() {
        mockMvc.perform(get("/person/tpordninger").header("fnr", testPerson1.fnr)
                .header("Authorization", "Bearer " + tokenHandler.getSignedToken(TokenHeaders.builder().build(), TokenClaims.builder().withDefaultClaims().build())))
                .andExpect(status().isOk)
    }

    @Test
    fun invalid_token_denied() {
        mockMvc.perform(get("/person/tpordninger").header("fnr", testPerson1.fnr)
                .header("Authorization", "Bearer " + TokenHandler().getSignedToken(TokenClaims.builder().withDefaultClaims().build())))
                .andExpect(status().isUnauthorized)
    }

    @Test
    fun no_token_denied() {
        mockMvc.perform(get("/person/tpordninger").header("fnr", testPerson1.fnr))
                .andExpect(status().isUnauthorized)
    }
}
