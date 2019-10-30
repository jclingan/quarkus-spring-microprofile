package org.acme.springmp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SalutationController {

    SalutationService salutationService;

    public SalutationController(SalutationService salutationService) {
        this.salutationService = salutationService;
    }

    @GetMapping
    @RequestMapping("/salutation")
    public String hello() {
        return salutationService.getSalutation();
    }
}