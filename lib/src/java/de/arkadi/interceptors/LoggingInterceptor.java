package de.arkadi.interceptors;


import de.arkadi.qualifier.Loggable;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

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
        Marker flyway = MarkerFactory.getMarker("FLYWAY");

        String className = ic.getTarget().getClass().getName();
        LOGGER.info(flyway, "Invoking : {} :: {}", className.substring(0, className.indexOf("$")), ic.getMethod().getName());
        LOGGER.info(flyway, "Parameters : {}", Arrays.toString(ic.getParameters()));

        Object result = ic.proceed();
        if (result == null) {
            result = "Void";
        }
        LOGGER.info("Return : {}", result.toString());
        return result;
    }
}



