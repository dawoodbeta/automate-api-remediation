package com.capgemini.demo.gateway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

public class PersonGateway {
  @InjectMocks private DemoGateway demoGateway;

  @BeforeEach
  public void setUp() {
    initMocks(this);
  }

  @Test
  public void testGetGreeting() {
    //act
    String greet = demoGateway.greeting("Abc");

    //assert
    assertEquals("Hello Abc!!!", greet);
  }
}
