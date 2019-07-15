package no.nav.tpregisteret.organisasjon;

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

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrganisationTests {

    private static String VALID_ORGNRA = "000000000";
    private static String VALID_ORGNRB = "111111111";
    private static String INVALID_ORGNR = "000000001";
    private static String VALID_TPNR_A = "1111";
    private static String VALID_TPNR_B = "4444";
    private static String INVALID_TPNR = "7777";

    @Autowired
    private MockMvc mockMvc;

    static class TestData implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(Arguments.of(VALID_ORGNRA, VALID_TPNR_A, status().isOk()),
                    Arguments.of(VALID_ORGNRA, INVALID_TPNR, status().isNotFound()),
                    Arguments.of(VALID_ORGNRA, VALID_TPNR_B, status().isNotFound()),
                    Arguments.of(VALID_ORGNRB, VALID_TPNR_B, status().isOk()),
                    Arguments.of(VALID_ORGNRB, VALID_TPNR_A, status().isNotFound()),
                    Arguments.of(VALID_ORGNRB, INVALID_TPNR, status().isNotFound()),
                    Arguments.of(INVALID_ORGNR, VALID_TPNR_A, status().isNotFound()),
                    Arguments.of(INVALID_ORGNR, VALID_TPNR_B, status().isNotFound()),
                    Arguments.of(INVALID_ORGNR, INVALID_TPNR, status().isNotFound()));
        }
    }

    @ParameterizedTest
    @ArgumentsSource(TestData.class)
    void testTpNrTilhorendeOrganisasjon(String orgNr, String tpNr, ResultMatcher expectedResult) throws Exception {
        mockMvc.perform(head("/organisasjon/" + VALID_ORGNRA + "/tpnr/" + VALID_TPNR_A))
                .andExpect(expectedResult)
                .andExpect(content().string(""));
    }
}