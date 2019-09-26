package no.nav.tpregisteret.organisation;

import no.nav.tpregisteret.tpordning.TpRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/organisation")
public class OrganisationController {

    private static final Logger LOG = LoggerFactory.getLogger(OrganisationController.class);

    private final TpRepository tpRepository;

    @Value("${orgnr.mapping}")
    private String orgnrMapping;

    public OrganisationController(TpRepository tpRepository) {
        this.tpRepository = tpRepository;
    }

    @GetMapping("/{orgnr}/tpnr/{tpnr}")
    public ResponseEntity getTpOrdningerForPerson(@PathVariable("orgnr") String orgnr, @PathVariable("tpnr") String tpnr) {
        if (validVaultOrgnrMapping(orgnr, tpnr)) {
            LOG.info("Valid vault mapping: orgnr " + orgnr + " for tpnr " + tpnr);
            return ResponseEntity.ok().build();
        }

        return tpRepository.getTpNrsForOrganisation(orgnr).contains(tpnr) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    private boolean validVaultOrgnrMapping(String orgnr, String tpnr) {
        var map = new HashMap<String, String>();
        LOG.info("Validate orgnr/tpnr:" + orgnr + "," + tpnr);
        for (var mapping : orgnrMapping.split("\\|")) {
            map.put(mapping.split(",")[0], mapping.split(",")[1]);
            LOG.info("Vault mapping: " + mapping.split(",")[0] + "," + mapping.split(",")[1]);
        }
        return map.containsKey(orgnr) && map.get(orgnr).equals(tpnr);
    }
}
