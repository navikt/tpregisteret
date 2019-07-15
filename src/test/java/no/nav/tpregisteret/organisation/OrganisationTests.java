package no.nav.tpregisteret.organisation;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrganisationTests {

    @Autowired
    private MockMvc mockMvc;

    static class TestData implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            String validOrgNrA = "000000000";
            String validOrgNrB = "111111111";
            String invalidOrgNr = "000000001";
            String validTpNrA = "1111";
            String validTpNrB = "4444";
            String invalidTpNr = "7777";
            Supplier<ResultMatcher> ownTpNr = () -> status().isOk();
            Supplier<ResultMatcher> doesntOwnTpNr = () -> status().isNotFound();
            return Stream.of(Arguments.of(validOrgNrA, validTpNrA, ownTpNr.get()),
                    Arguments.of(validOrgNrA, invalidTpNr, doesntOwnTpNr.get()),
                    Arguments.of(validOrgNrA, validTpNrB, doesntOwnTpNr.get()),
                    Arguments.of(validOrgNrB, validTpNrB, ownTpNr.get()),
                    Arguments.of(validOrgNrB, validTpNrA, doesntOwnTpNr.get()),
                    Arguments.of(validOrgNrB, invalidTpNr, doesntOwnTpNr.get()),
                    Arguments.of(invalidOrgNr, validTpNrA, doesntOwnTpNr.get()),
                    Arguments.of(invalidOrgNr, validTpNrB, doesntOwnTpNr.get()),
                    Arguments.of(invalidOrgNr, invalidTpNr, doesntOwnTpNr.get()));
        }
    }

    @ParameterizedTest
    @ArgumentsSource(TestData.class)
    void testTpNrTilhorendeOrganisasjonHead(String orgNr, String tpNr, ResultMatcher expectedResult) throws Exception {
        mockMvc.perform(head("/organisation/" + orgNr + "/tpnr/" + tpNr))
                .andExpect(expectedResult)
                .andExpect(content().string(""));
    }

    @ParameterizedTest
    @ArgumentsSource(TestData.class)
    void testTpNrTilhorendeOrganisasjonGet(String orgNr, String tpNr, ResultMatcher expectedResult) throws Exception {
        mockMvc.perform(get("/organisation/" + orgNr + "/tpnr/" + tpNr))
                .andExpect(expectedResult)
                .andExpect(content().string(""));
    }
}