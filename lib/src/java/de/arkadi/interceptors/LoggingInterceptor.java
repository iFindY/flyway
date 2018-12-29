package de.arkadi.interceptors;


import de.arkadi.qualifier.Loggable;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.Arrays;


@Loggable
@Interceptor
public class LoggingInterceptor {

    @Inject
    private Logger logger;

    @AroundInvoke
    public Object intercept(InvocationContext ic) throws Exception {
        String className = ic.getTarget().getClass().getName();
        logger.info("Invoking : {} :: {}", className.substring(0, className.indexOf("$")), ic.getMethod().getName());
        logger.info("Parameters : {}", Arrays.toString(ic.getParameters()));

        Object result = ic.proceed();
        if (result == null) {
            result = "Void";
        }
        logger.info("Return : {}", result.toString());
        return result;
    }
}



