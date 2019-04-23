package no.nav.tpregisteret;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TpEndpoints {

    @Autowired
    private TpMetrics metrics;
    
    private static final Logger LOG = LoggerFactory.getLogger(TpEndpoints.class);

    @GetMapping("/tpregisteret")
    public void tpEndpoint() {
        metrics.requestCounter.increment();
        LOG.info("Restkall til tpregisteret");
    }
}
