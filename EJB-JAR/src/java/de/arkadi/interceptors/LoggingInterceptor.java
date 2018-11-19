package de.arkadi.interceptors;

import de.arkadi.utils.Loggable;
import de.arkadi.utils.LoggingUtils;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

import static de.arkadi.utils.LoggingUtils.Type.UTIL;

@Loggable
@Interceptor
public class LoggingInterceptor {

    @Inject
    @LoggingUtils(UTIL)
    private Logger logger;

    @AroundInvoke
    public Object intercept(InvocationContext ic) throws Exception {
        logger.entering("producer invoking : " + ic.getTarget().getClass().getName(), ic.getMethod().getName());
        try {
            logger.info("with context data : " + ic.getContextData().keySet().toString());

            return ic.proceed();
        } finally {
            logger.exiting("invocation complected : " + ic.getTarget().getClass().getName(), ic.getMethod().getName());

        }
    }
}



