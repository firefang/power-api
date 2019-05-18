package io.github.firefang.power.engine.expression.keyword;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Interface used to find keywords
 * 
 * @author xinufo
 *
 */
public interface IKeywordFinder {

    /**
     * Find all the keywords
     * 
     * @return
     */
    Map<String, Method> find();

}
