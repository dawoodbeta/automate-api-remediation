package com.capgemini.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

import com.capgemini.demo.service.DemoService;
import com.capgemini.demo.service.dto.PersonDto;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class DemoControllerTest {

  @InjectMocks private DemoController demoController;

  @Mock private DemoService demoService;

  @BeforeEach
  public void setUp() {
    initMocks(this);
  }

  @Test
  public void testGetGreeting() {
    //assign
    when(demoService.greeting(anyString())).thenReturn("Hello Abc!!!");

    //act
    String greet = demoController.getGreeting("Abc");

    //assert
    assertEquals("Hello Abc!!!", greet);
  }

  @Test
  public void addPerson() {
    //assign
    PersonDto personDto = new PersonDto("abc", "abc@xyz.com");
    doNothing().when(demoService).addPerson(personDto);

    //act
    demoController.addPerson(personDto);

    //assert
    verify(demoService, times(1)).addPerson(personDto);
  }

  @Test
  public void getPerson() {
    //assign
    when(demoService.getPersons()).thenReturn(Arrays.asList());

    //act
    List<PersonDto> persons = demoController.getPersons();

    //assert
    assertNotNull(persons);
  }
}
