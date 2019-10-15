package no.nav.tpregisteret.tjenestepensjon;

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tjenestepensjon")
class TjenestepensjonController {

    @RequestMapping("/forhold")
    class Forhold {
        @GetMapping("{forholdId}")
        fun hentTpforhold(@PathVariable("forholdId") fnr: String): String = ""

        @PutMapping("{forholdId}")
        fun lagreTpforhold(@PathVariable("forholdId") fnr: String): String = ""
    }

    @RequestMapping("/ytelse")
    class Ytelse {
        @GetMapping("{ytelseId}")
        fun lagreYtelse(@PathVariable("ytelseId") ytelseId: String): String = ""

        @PutMapping("{ytelseId}")
        fun hentYtelse(@PathVariable("ytelseId") ytelseId: String): String = ""
    }

    @RequestMapping("/simulering")
    class Simulering {
        @GetMapping("{simuleringId}")
        fun lagreSimulering(@PathVariable("simuleringId") simuleringId: String): String = ""

        @PutMapping("{ytelseId}")
        fun hentSimulering(@PathVariable("simuleringId") simuleringId: String): String = ""
    }
}
