package com.novomind.ipim.core.util.arkadi.qualifier;


import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Retention(RUNTIME)
@Target({FIELD, TYPE, METHOD})
public @interface FlyWayTarget {
    Target value() default Target.NON;

    public enum Target {
        CORE_BASELINE,
        CORE_RELEASES,
        PROJECT_PRE,
        PROJECT_POST,
        NON
    }
}
