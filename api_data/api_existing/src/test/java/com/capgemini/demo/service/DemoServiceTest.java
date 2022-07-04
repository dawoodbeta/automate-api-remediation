package com.capgemini.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

import com.capgemini.demo.gateway.DemoGateway;
import com.capgemini.demo.repository.PersonRepository;
import com.capgemini.demo.repository.enity.PersonEntity;
import com.capgemini.demo.service.dto.PersonDto;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class DemoServiceTest {

  @InjectMocks private DemoService demoService;

  @Mock private DemoGateway demoGateway;

  @Mock private PersonRepository personRepository;

  @BeforeEach
  public void setUp() {
    initMocks(this);
  }

  @Test
  public void testGetGreeting() {
    //assign
    when(demoGateway.greeting(anyString())).thenReturn("Hello Abc!!!");

    //act
    String greet = demoService.greeting("Abc");

    //assert
    assertEquals("Hello Abc!!!", greet);
  }

  @Test
  public void addPerson() {
    //assign
    PersonEntity personEntity = new PersonEntity();
    personEntity.setName("abc");
    personEntity.setEmailAddress("abc@xyz.com");
    when(personRepository.save(personEntity)).thenReturn(new PersonEntity());
    PersonDto personDto = new PersonDto("abc", "abc@xyz.com");

    //act
    demoService.addPerson(personDto);

    //assert
    verify(personRepository, times(1)).save(personEntity);
  }

  @Test
  public void getPerson() {
    //assign
    when(personRepository.findAll()).thenReturn(Arrays.asList());

    //act
    List<PersonDto> persons = demoService.getPersons();

    //assert
    assertNotNull(persons);
  }
}
