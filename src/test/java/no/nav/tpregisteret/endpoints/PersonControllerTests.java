package no.nav.tpregisteret.endpoints;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import no.nav.tokentest.TokenClaims;
import no.nav.tokentest.TokenHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
class PersonControllerTests {

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
    void valid_parameter_returns_200() throws Exception {
        mockMvc.perform(get("/person/" + testPerson1.getFnr() + "/tpordninger")
                .header("Authorization", "Bearer " + tokenHandler.getSignedToken(TokenClaims.builder().withDefaultClaims().build())))
                .andExpect(status().isOk());
    }

    @Test
    void root_returns_404() throws Exception {
        mockMvc.perform(get("/")
                .header("Authorization", "Bearer " + tokenHandler.getSignedToken(TokenClaims.builder().withDefaultClaims().build())))
                .andExpect(status().isNotFound());
    }
}