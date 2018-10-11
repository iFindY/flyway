package de.arkadi.tryOut.server;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class StartUpEJB {

  @PostConstruct
  public void init() {

    System.out.println("hallo Arkadi");
  }

  void hello() {

    System.out.println("hi");
  }
}
