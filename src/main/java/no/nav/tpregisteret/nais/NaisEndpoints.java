package no.nav.tpregisteret.nais;

import no.nav.tpregisteret.DbConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    private DbConnector dbConnector;

    @GetMapping("/test")
    public String test(@RequestParam String fnr) { return "Secret: " + dbConnector.getTPIDs(fnr); }
}