package no.nav.tpregisteret;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TpEndpoints {

    @Autowired
    private DbConnector db;

    private static final Logger LOG = LoggerFactory.getLogger(TpEndpoints.class);

    @GetMapping("/gettpnr")
    public ResponseEntity<String> getTpIds(@RequestParam(value = "fnr") String fnr) {
        LOG.info("Restkall til tpregisteret");

        try {
            return new ResponseEntity(db.getTPIDs(fnr), HttpStatus.OK);
        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public DbConnector getDb() {
        return db;
    }

    public void setDb(DbConnector db) {
        this.db = db;
    }
}
