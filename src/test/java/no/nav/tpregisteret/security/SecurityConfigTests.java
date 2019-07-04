package no.nav.tpregisteret.security;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import no.nav.tokentest.TokenClaims;
import no.nav.tokentest.TokenHandler;
import no.nav.tokentest.TokenHeaders;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static no.nav.tpregisteret.TestPerson.testPerson1;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTests {

    @Autowired
    private MockMvc mockMvc;

    private WireMockServer wireMockServer;

    @Autowired
    private TokenHandler tokenHandler;

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer(8090);
        wireMockServer.start();
        wireMockServer.stubFor(WireMock.get(urlEqualTo("/sts/jwks"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withStatus(200).withBody(tokenHandler.getJWKS("ignored"))));
    }

    @AfterEach
    public void teardown() {
        wireMockServer.stop();
    }

    @Test
    public void liveness_and_actuator_permitted() throws Exception {
        mockMvc.perform(get("/actuator/prometheus")).andExpect(status().isOk());
        mockMvc.perform(get("/isAlive")).andExpect(status().isOk());
        mockMvc.perform(get("/isReady")).andExpect(status().isOk());
    }

    @Test
    public void valid_token_authenticated() throws Exception {
        mockMvc.perform(get("/person/" + testPerson1.getFnr() + "/tpordninger")
            .header("Authorization", "Bearer " + tokenHandler.getSignedToken(TokenHeaders.builder().build(), TokenClaims.builder().withDefaultClaims().build())))
            .andExpect(status().isOk());
    }

    @Test
    public void invalid_token_denied() throws Exception {
        mockMvc.perform(get("/person/" + testPerson1.getFnr() + "/tpordninger")
            .header("Authorization", "Bearer " + new TokenHandler().getSignedToken(TokenClaims.builder().withDefaultClaims().build())))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void no_token_denied() throws Exception {
        mockMvc.perform(get("/person/" + testPerson1.getFnr() + "/tpordninger"))
            .andExpect(status().isUnauthorized());
    }
}
