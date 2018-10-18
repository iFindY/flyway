package de.arkadi.Interceptors.migration;

import de.arkadi.producer.Produce;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;


@Interceptor
@Loggable
public class LoggingInterceptor {

    @Inject
    @Produce(Produce.Type.util)
    private Logger logger;

    @AroundInvoke
    public Object logMethod(InvocationContext ic) throws Exception {
        logger.entering(ic.getTarget().getClass().getName(), ic.getMethod().getName());
        logger.info("IN " + ic.getTimer());
        try {
            logger.info(ic.getContextData().keySet().toString());
            return ic.proceed();
        } finally {
            logger.exiting(ic.getTarget().getClass().getName(), ic.getMethod().getName());
            logger.info("OUT " + ic.getTimer());

        }
    }
}



