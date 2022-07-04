package com.capgemini.demo.controller;

import com.capgemini.demo.service.DemoService;
import com.capgemini.demo.service.dto.PersonDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class DemoController {

  @Autowired private DemoService demoService;

  @GetMapping("/greeting/{person}")
  public String getGreeting(@PathVariable("person") String person) {
    return demoService.greeting(person);
  }

  @PostMapping("/person")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void addPerson(@RequestBody PersonDto personDto) {
    demoService.addPerson(personDto);
  }

  @GetMapping("/persons")
  public List<PersonDto> getPersons() {
    return demoService.getPersons();
  }
}
