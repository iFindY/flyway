package de.arkadi.interceptors;

import de.arkadi.utils.Loggable;
import de.arkadi.utils.LoggingUtils;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.Arrays;
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
        String className = ic.getTarget().getClass().getName();
        logger.info("Invoking : " + className.substring(0, className.indexOf("$")) + ":: " + ic.getMethod().getName());
        logger.info("Parameters : " + Arrays.toString(ic.getParameters()));

        Object result = ic.proceed();
        if (result == null) {
            result = "Void";
        }
        logger.info("Return : " + result.toString());
        return result;
    }
}



