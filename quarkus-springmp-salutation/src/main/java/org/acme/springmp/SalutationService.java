package org.acme.springmp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
public class SalutationService {
    final String salutation;

    public SalutationService(@Value("${salutation}") String salutation) {
        this.salutation = salutation;
    }
    public String getSalutation() {

	long time=0;

        // Simulate a slow service to trigger timeout on caller
        try { TimeUnit.MILLISECONDS.sleep(time = (int)(Math.random()*1000));
        } catch(Exception ex) {
        }

	System.out.println("Timeout was " + time + " milliseconds");

        return salutation;
    }
}
