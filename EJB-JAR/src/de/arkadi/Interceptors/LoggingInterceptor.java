package de.arkadi.Interceptors;

import de.arkadi.producer.LoggingUtils;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

import static de.arkadi.producer.LoggingUtils.Type.UTIL;


@Interceptor
@Loggable
public class LoggingInterceptor {

    @Inject
    @LoggingUtils(UTIL)
    private Logger logger;

    @AroundInvoke
    public Object logMethod(InvocationContext ic) throws Exception {
        logger.entering(ic.getTarget().getClass().getName(), ic.getMethod().getName());
        logger.info("IN " + ic.getTimer());
        try {
            logger.info(ic.getContextData().keySet().toString());
            logger.info("hi im here ");
            return ic.proceed();
        } finally {
            logger.exiting(ic.getTarget().getClass().getName(), ic.getMethod().getName());
            logger.info("hi im out here !!");

        }
    }
}



