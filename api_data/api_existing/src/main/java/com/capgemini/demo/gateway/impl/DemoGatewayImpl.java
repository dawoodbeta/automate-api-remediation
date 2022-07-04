package com.capgemini.demo.gateway.impl;

import com.capgemini.demo.gateway.DemoGateway;
import org.springframework.stereotype.Component;

@Component("demoGateway")
public class DemoGatewayImpl implements DemoGateway {
  @Override
  public String greeting(String person) {
    return "Hello " + person + "!!!";
  }
}
