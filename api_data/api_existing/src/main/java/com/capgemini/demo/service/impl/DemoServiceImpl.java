package com.capgemini.demo.service.impl;

import static org.hibernate.internal.util.collections.CollectionHelper.isEmpty;

import com.capgemini.demo.gateway.DemoGateway;
import com.capgemini.demo.repository.PersonRepository;
import com.capgemini.demo.repository.enity.PersonEntity;
import com.capgemini.demo.service.DemoService;
import com.capgemini.demo.service.dto.PersonDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("demoService")
public class DemoServiceImpl implements DemoService {

  @Autowired private DemoGateway demoGateway;

  @Autowired private PersonRepository personRepository;

  @Override
  public String greeting(String person) {
    return demoGateway.greeting(person);
  }

  @Override
  public void addPerson(PersonDto personDto) {
    PersonEntity pe = new PersonEntity();
    pe.setName(personDto.getName());
    pe.setEmailAddress(personDto.getEmailAddress());
    personRepository.save(pe);
  }

  @Override
  public List<PersonDto> getPersons() {
    List<PersonEntity> pes = personRepository.findAll();
    List<PersonDto> pdtos = new ArrayList<>();
    if (!isEmpty(pes)) {
      pdtos =
          pes.stream()
              .map(pe -> new PersonDto(pe.getName(), pe.getEmailAddress()))
              .collect(Collectors.toList());
    }
    return pdtos;
  }
}
