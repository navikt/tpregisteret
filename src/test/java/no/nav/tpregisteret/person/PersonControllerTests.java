package no.nav.tpregisteret.person;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import no.nav.tokentest.TokenClaims;
import no.nav.tokentest.TokenHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static no.nav.tpregisteret.TestPerson.testPerson1;
import static no.nav.tpregisteret.TestPerson.testPerson2;
import static no.nav.tpregisteret.TestPerson.testPerson3;
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
    void valid_parameter_returns_200_and_empty_result() throws Exception {
        mockMvc.perform(get("/person/" + testPerson1.getFnr() + "/tpordninger")
                .header("Authorization", "Bearer " + getValidSignedToken()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    void valid_parameter_returns_200_and_single_result() throws Exception {
        mockMvc.perform(get("/person/" + testPerson2.getFnr() + "/tpordninger")
                .header("Authorization", "Bearer " + getValidSignedToken()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{\"tssId\":\"11111111111\",\"tpId\":\"1111\",\"orgNr\":\"000000000\",\"navn\":\"TP1\"}]"));
    }

    @Test
    void valid_parameter_returns_200_and_results() throws Exception {
        mockMvc.perform(get("/person/" + testPerson3.getFnr() + "/tpordninger")
                .header("Authorization", "Bearer " + getValidSignedToken()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{\"tssId\":\"11111111111\",\"tpId\":\"1111\",\"orgNr\":\"000000000\",\"navn\":\"TP1\"},{\"tssId\":\"22222222222\",\"tpId\":\"2222\",\"orgNr\":\"000000000\",\"navn\":\"TP2\"}]"));
    }

    @Test
    void invalid_token_returns_401() throws Exception {
        mockMvc.perform(get("/person/" + testPerson3.getFnr() + "/tpordninger")
                .header("Authorization", "Bearer " + getInvalidSignedToken()))
                .andExpect(status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    void root_returns_404() throws Exception {
        mockMvc.perform(get("/")
                .header("Authorization", "Bearer " + getValidSignedToken()))
                .andExpect(status().isNotFound());
    }

    private String getValidSignedToken() {
        return tokenHandler.getSignedToken(TokenClaims.builder().withDefaultClaims().build());
    }

    private String getInvalidSignedToken() {
        return new TokenHandler().getSignedToken(TokenClaims.builder().withDefaultClaims().build());
    }
}