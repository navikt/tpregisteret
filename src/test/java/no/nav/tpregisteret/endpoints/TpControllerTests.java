package no.nav.tpregisteret.endpoints;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static no.nav.tpregisteret.TestPerson.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TpControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private final String baseURI = "/person/tpforhold";

    @Test
    public void valid_parameter_returns_200() throws Exception {
        mockMvc.perform(get(baseURI + "?fnr=" + testPerson1.getFnr())).andExpect(status().isOk());
    }

    @Test
    public void no_parameter_returns_400() throws Exception {
        mockMvc.perform(get(baseURI)).andExpect(status().isBadRequest());
    }

    @Test
    public void root_returns_404() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isNotFound());
    }
}