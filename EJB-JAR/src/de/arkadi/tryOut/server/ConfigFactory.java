package de.arkadi.tryOut.server;

import de.arkadi.tryOut.model.Arkadi;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

@Stateless
public class ConfigFactory {

    @Produces
    @Named(value = "HAMBURG")
    static String h = "hello Hamburg";

    @Produces
    @Named(value = "BERLIN")
    static String b = "hello Berlin";

    @Produces
    static Integer i = 5;

    @Produces
    public double getDouble() {

        return 1 + 1;
    }

    @Produces
    public Arkadi createMe() {

        //arkadi.setCreator("ConfigFactory");
        return new Arkadi("Na du wurdes erstellt");
    }

    @Produces
    public HelloUserBean createHelloBean() {

        //helloUserBean.setCreator("HelloBean: ConfigFactory");
        return new HelloUserBean("du wurdes auch erstellt, du produkt meiner arbeit");
    }

}



