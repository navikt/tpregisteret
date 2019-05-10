package no.nav.tpregisteret.endpoints;

import no.nav.tpregisteret.database.DbConnector;
import no.nav.tpregisteret.mapper.ListToJsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TpController {

    @Autowired
    private DbConnector db;

    private static final Logger LOG = LoggerFactory.getLogger(TpController.class);

    @GetMapping("/person/tpforhold")
    public ResponseEntity<String> getTpIds(@RequestParam(value = "fnr") String fnr) {
        LOG.info("Restkall til tpregisteret");
        try {
            return new ResponseEntity<>(ListToJsonMapper.convertToJson(db.getTPOrdninger(fnr)), HttpStatus.OK);
        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
