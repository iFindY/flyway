package de.arkadi.tryOut.server;


import de.arkadi.tryOut.model.Arkadi;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Stateless(name = "myService")
public class SomeService {

  @Inject()
  @Named(value = "HAMBURG")
  String myString1;
  @Inject()
  @Named(value = "BERLIN")
  String myString2;
  @Inject
  Integer myInteger;
  @Inject
  double myDouble;
  @Inject
  Arkadi arkadi;
  @Inject
  HelloUserBean helloU;

  public void shout() {

    arkadi.sayKu();
    arkadi.getCreator();
    System.out.println("AAAAA!");

    System.out.println(myString1);
    System.out.println(myString2);
  }

  @Inject()
  public void sayMyName(Arkadi arkadi, HelloUserBean helloUserBean) {

    arkadi.getCreator();
    helloUserBean.printCreator();
    helloU.sayHello("hellou my Q");
  }


}
