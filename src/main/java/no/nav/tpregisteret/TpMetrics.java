package no.nav.tpregisteret;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class TpMetrics {

    private MeterRegistry registry;

    public final Counter requestCounter;
    public final Counter dbErrorCounter;

    public TpMetrics(MeterRegistry registry) {
        this.registry = registry;

        requestCounter = Counter
                .builder("tpforhold_request_counter")
                .description("Antall godkjente kall mot pip-tpforhold")
                .register(registry);

        dbErrorCounter = Counter
                .builder("tpforhold_db_error_counter")
                .description("Antall SQL Exceptions i pip-tpforhold")
                .register(registry);
    }
}
