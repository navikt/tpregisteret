package no.nav.tpregisteret.person

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import no.nav.tpregisteret.TestPerson.Companion.testPerson1
import no.nav.tpregisteret.TestPerson.Companion.testPerson2
import no.nav.tpregisteret.TestPerson.Companion.testPerson3

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

import no.nav.tokentest.TokenClaims
import no.nav.tokentest.TokenHandler

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTests {

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
    fun valid_parameter_returns_200_and_empty_result() {
        mockMvc.perform(get("/person/tpordninger").header("fnr", testPerson1.fnr)
                .header("Authorization", "Bearer " + getValidSignedToken()))
                .andExpect(status().isOk)
                .andExpect(MockMvcResultMatchers.content().json("[]"))
    }

    @Test
    fun valid_parameter_returns_200_and_single_result() {
        mockMvc.perform(get("/person/tpordninger").header("fnr", testPerson2.fnr)
                .header("Authorization", "Bearer " + getValidSignedToken()))
                .andExpect(status().isOk)
                .andExpect(MockMvcResultMatchers.content().json("[{\"tssId\":\"11111111111\",\"tpId\":\"1111\",\"orgNr\":\"000000000\",\"navn\":\"TP1\"}]"))
    }

    @Test
    fun valid_parameter_returns_200_and_results() {
        mockMvc.perform(get("/person/tpordninger").header("fnr", testPerson3.fnr)
                .header("Authorization", "Bearer " + getValidSignedToken()))
                .andExpect(status().isOk)
                .andExpect(MockMvcResultMatchers.content().json("[{\"tssId\":\"11111111111\",\"tpId\":\"1111\",\"orgNr\":\"000000000\",\"navn\":\"TP1\"},{\"tssId\":\"22222222222\",\"tpId\":\"2222\",\"orgNr\":\"000000000\",\"navn\":\"TP2\"}]"))
    }

    @Test
    fun invalid_token_returns_401() {
        mockMvc.perform(get("/person/tpordninger").header("fnr", testPerson3.fnr)
                .header("Authorization", "Bearer " + getInvalidSignedToken()))
                .andExpect(status().isUnauthorized)
                .andExpect(MockMvcResultMatchers.content().string(""))
    }

    @Test
    fun root_returns_404() {
        mockMvc.perform(get("/")
                .header("Authorization", "Bearer " + getValidSignedToken()))
                .andExpect(status().isNotFound)
    }

    private fun getValidSignedToken(): String = tokenHandler.getSignedToken(TokenClaims.builder().withDefaultClaims().build())

    private fun getInvalidSignedToken(): String = TokenHandler().getSignedToken(TokenClaims.builder().withDefaultClaims().build())
}