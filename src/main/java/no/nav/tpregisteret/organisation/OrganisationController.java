package no.nav.tpregisteret.organisation;

import no.nav.tpregisteret.tpordning.TpRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organisation")
public class OrganisationController {

    private final TpRepository tpRepository;

    public OrganisationController(TpRepository tpRepository) {
        this.tpRepository = tpRepository;
    }

    @GetMapping("/{orgnr}/tpnr/{tpnr}")
    public ResponseEntity getTpOrdningerForPerson(@PathVariable("orgnr") String orgnr, @PathVariable("tpnr") String tpnr) {
        return tpRepository.getTpNrsForOrganisation(orgnr).contains(tpnr) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
