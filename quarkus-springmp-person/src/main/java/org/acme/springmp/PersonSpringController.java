package org.acme.springmp;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonSpringController {

    private final PersonSpringRepository personRepository;
    private final PersonSpringMPService personService;

    public PersonSpringController(PersonSpringRepository personRepository, PersonSpringMPService personService) {
        this.personRepository = personRepository;
        this.personService = personService;
    }

    @GetMapping(path = "/greet/{id}", produces = "text/plain")
    public String greetPerson(@PathVariable(name = "id") long id) {
        Optional<Person> person = personRepository.findById(id);

        String name;

        if (person.isPresent()) {
            name = personService.getSalutation() + " " + person.get().getName();
        } else {
            name = "Not found";
        }
        return name;
    }

    @GetMapping(produces = "application/json")
    public Iterable<Person> findAll() {
        return personRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") long id) {
        personRepository.deleteById(id);
    }

    @Transactional
    @PostMapping(path = "/name/{name}/age/{age}", produces = "application/json")
    public Person create(@PathVariable(name = "name") String name, @PathVariable(name = "age") Integer age) {
        return personRepository.save(new Person(name, age));
    }

    @GetMapping(path = "/age/{age}", produces = "application/json")
    public List<Person> findByColor(@PathVariable(name = "age") int age) {
        return personRepository.findByAge(age);
    }

}