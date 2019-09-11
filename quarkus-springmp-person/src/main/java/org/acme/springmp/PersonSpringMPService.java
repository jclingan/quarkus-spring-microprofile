package org.acme.springmp;

import javax.inject.Inject;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.springframework.beans.factory.annotation.Value;

public class PersonSpringMPService {

    @Inject
    @RestClient
    SalutationMicroProfileRestClient salutationRestClient;

    @Value("${fallbackSalutation}")
    String fallbackSalutation;

    @Timeout(value = 500 )
    @Fallback(fallbackMethod = "salutationFallback")
    public String getSalutation() {
        String salutation = salutationRestClient.getSalutation();

        return salutation;
    }

    @Counted(name = "salutationFallback")
    public String salutationFallback() {
        return fallbackSalutation;
    }

}
