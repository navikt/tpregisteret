package no.nav.tpregisteret.person;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.nav.tpregisteret.tpordning.TpRepository;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final TpRepository tpRepository;

    public PersonController(TpRepository tpRepository) {
        this.tpRepository = tpRepository;
    }

    @GetMapping("/{fnr}/tpordninger")
    public ResponseEntity getTpOrdningerForPerson(@PathVariable("fnr") String fnr) {
        return ResponseEntity.ok(tpRepository.getTpOrdningerForPerson(fnr));
    }
}
