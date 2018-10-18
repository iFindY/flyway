package de.arkadi.producer;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.logging.Logger;


public class Producer {

    @Produces
    @Produce(Produce.Type.util)
    public Logger produceLoggerUtil(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

    @Produces
    @Produce(Produce.Type.slf4j)
    public org.slf4j.Logger produceLoggerSLF4J(InjectionPoint injectionPoint) {
        return org.slf4j.LoggerFactory.getLogger(injectionPoint.getBean().getBeanClass());
    }

}