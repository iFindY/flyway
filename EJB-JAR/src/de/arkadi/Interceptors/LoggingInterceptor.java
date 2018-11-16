package de.arkadi.Interceptors;

import de.arkadi.utils.LoggingUtils;
import de.arkadi.utils.Loggable;

import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

@Interceptor
@Loggable
public class LoggingInterceptor {

    @Inject
    //@LoggingUtils(UTIL)
    @Named("util")
    private Logger logger;

    @AroundInvoke
    public Object logMethod(InvocationContext ic) throws Exception {
        logger.entering(ic.getTarget().getClass().getName(), ic.getMethod().getName());
        logger.info("going in !! " + ic.getTimer());
        try {
            logger.info(ic.getContextData().keySet().toString());
            logger.info("hi, im here !!");
            return ic.proceed();
        } finally {
            logger.exiting(ic.getTarget().getClass().getName(), ic.getMethod().getName());
            logger.info("hi, im out here !!");

        }
    }
}



