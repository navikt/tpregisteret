package no.nav.tpregisteret;

import no.nav.tokentest.TokenHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenHandlerConfig {
    @Bean
    public TokenHandler tokenHandler() {
        return new TokenHandler();
    }
}
