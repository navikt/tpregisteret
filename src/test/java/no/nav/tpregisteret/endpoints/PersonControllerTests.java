package no.nav.tpregisteret.endpoints;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static no.nav.tpregisteret.TestPerson.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void valid_parameter_returns_200() throws Exception {
        mockMvc.perform(get("/person/"+testPerson1.getFnr()+"/tpordninger")).andExpect(status().isOk());
    }

    @Test
    void root_returns_404() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isNotFound());
    }
}