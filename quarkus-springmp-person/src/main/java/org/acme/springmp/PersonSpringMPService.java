package org.acme.springmp;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.quarkus.runtime.annotations.RegisterForReflection;

// RegisterForReflection will enable this app to run in native mode, otherwise
// the fallback method will be "dead code eliminated". Note, this can be done
// via configuration instead of using an annotation, but for this example the
// annotation makes it more obvious. See the Native Application Tips Guide for
// taking the configuration approach:
// https://quarkus.io/guides/writing-native-applications-tips

@RegisterForReflection
@Service
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
