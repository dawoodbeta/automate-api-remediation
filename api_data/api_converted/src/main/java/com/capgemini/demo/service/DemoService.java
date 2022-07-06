package com.capgemini.demo.service;

import com.capgemini.demo.service.dto.PersonDto;
import java.util.List;

public interface DemoService {
  String greeting(String person);

  void addPerson(PersonDto personDto);

  List<PersonDto> getPersons();
}
