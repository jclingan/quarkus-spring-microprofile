package org.acme.springmp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PersonSpringRepository extends CrudRepository<Person, Long> {
    List<Person> findByAge(int age);
}