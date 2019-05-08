package no.nav.tpregisteret;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class EndpointTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TpEndpoints endpoint;

    private final String baseURI = "/gettpnr";

    @Test
    public void http_200_endepunkt() throws Exception {
        this.mockMvc.perform(get(baseURI + "?fnr=12345612345"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void http_400_uten_parametere() throws Exception {
        this.mockMvc.perform(get(baseURI))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void http_404_unmapped_endepunkt() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void http_500_sql_error() throws Exception {
        DbConnector validConn = endpoint.getDb();
        endpoint.setDb(null); // Force SQLException

        this.mockMvc.perform(get(baseURI + "?fnr=00000000000"))
                .andDo(print()).andExpect(status().isInternalServerError());

        endpoint.setDb(validConn);
    }
}