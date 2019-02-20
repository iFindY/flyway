package com.novomind.ipim.core.util.arkadi.interceptors;


import com.novomind.ipim.core.util.arkadi.qualifier.Loggable;
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
    private Logger LOGGER;

    @AroundInvoke
    public Object intercept(InvocationContext ic) throws Exception {
        String className = ic.getTarget().getClass().getName();
        LOGGER.info("Invoking : {} :: {}", className.substring(0, className.indexOf("$")), ic.getMethod().getName());
        LOGGER.info("Parameters : {}", Arrays.toString(ic.getParameters()));

        Object result = ic.proceed();
        if (result == null) {
            result = "Void";
        }
        LOGGER.info("Return : {}", result.toString());
        return result;
    }
}



