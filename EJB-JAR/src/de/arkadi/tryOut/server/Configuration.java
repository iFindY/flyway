package de.arkadi.tryOut.server;

import de.arkadi.tryOut.model.Arkadi;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

@Stateless
public class Configuration {

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

        Arkadi arkadi = new Arkadi();
        arkadi.setCreator("Configuration");

        return new Arkadi();
    }

    @Produces
    @ApplicationScoped
    public HelloUserBean createHelloBean() {

        HelloUserBean helloUserBean = new HelloUserBean();
        helloUserBean.setCreator("HelloBean: Configuration");
        return helloUserBean;
    }

}



