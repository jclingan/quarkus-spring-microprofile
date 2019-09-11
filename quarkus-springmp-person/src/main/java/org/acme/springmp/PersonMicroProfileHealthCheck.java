package org.acme.springmp;

import javax.inject.Inject;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.rest.client.inject.RestClient;

// This Health Check will always return UP since the
// salutation falls back to a default value, but this check
// will provide status on the salutation state in case a
// custom probe wants to check the value.

@Readiness
public class PersonMicroProfileHealthCheck implements HealthCheck {

    @Inject
    @RestClient
    SalutationMicroProfileRestClient salutationClient;

    @Override
    public HealthCheckResponse call() {

        String salutation = "UP";
        try {
            salutationClient.getSalutation();
        } catch (Exception ex) {
            salutation = "DOWN";
        }

        return HealthCheckResponse.named("salutationCheck").up().withData("salutationState", salutation).build();
    }
}