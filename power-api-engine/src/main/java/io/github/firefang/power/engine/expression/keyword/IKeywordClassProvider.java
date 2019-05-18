package io.github.firefang.power.engine.expression.keyword;

import java.util.List;

/**
 * Interface used to finder classes contain keywords by SPI
 * 
 * @author xinufo
 *
 */
public interface IKeywordClassProvider {

    /**
     * Get all classes contain methods annotated by {@link Keyword}
     * 
     * @return
     */
    List<Class<?>> keywordClasses();

}
