package com.capgemini.demo.service.dto;

public class PersonDto {
  private String name;

  private String emailAddress;

  public PersonDto() {}

  public PersonDto(String name, String emailAddress) {
    this.name = name;
    this.emailAddress = emailAddress;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }
}
