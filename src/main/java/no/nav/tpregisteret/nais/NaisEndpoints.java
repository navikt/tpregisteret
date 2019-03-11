package no.nav.tpregisteret.nais;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NaisEndpoints {

    @GetMapping("/isAlive")
    public HttpStatus isAlive() {
        return HttpStatus.OK;
    }

    @GetMapping("/isReady")
    public HttpStatus isReady() {
        return HttpStatus.OK;
    }

    @Value("${TEST_SECRET}")
    private String test;

    @GetMapping("/test")
    public String test() { return test; }
}