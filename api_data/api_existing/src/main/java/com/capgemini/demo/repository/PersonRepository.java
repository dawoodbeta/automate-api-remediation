package com.capgemini.demo.repository;

import com.capgemini.demo.repository.enity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {}
