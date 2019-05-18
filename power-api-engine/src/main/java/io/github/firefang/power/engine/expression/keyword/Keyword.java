package io.github.firefang.power.engine.expression.keyword;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declare a method to be keyword, the method must be static
 * 
 * @author xinufo
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Keyword {

    /**
     * Name of the keyword. The name must be a legal java identifier
     * 
     * @return
     */
    String value();

}
