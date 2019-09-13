package org.acme.springmp;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class PersonSpringMPService {

    @Autowired
    @RestClient
    SalutationMicroProfileRestClient salutationRestClient;

    @Value("${fallbackSalutation}")
    String fallbackSalutation;

    @Timeout(500)
    @Fallback(fallbackMethod = "salutationFallback")
    public String getSalutation() {
        String salutation = salutationRestClient.getSalutation();

        return salutation;

    }

    //@Metric // Commented out to simplify metrics output
    @Counted(name = "fallbackCounter", displayName = "Fallback Counter", description = "Salutation Fallback Counter")
    public String salutationFallback() {
        return fallbackSalutation;
    }

}
