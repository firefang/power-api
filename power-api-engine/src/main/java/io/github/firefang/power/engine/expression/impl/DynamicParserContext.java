package io.github.firefang.power.engine.expression.impl;

import java.util.regex.Pattern;

import org.springframework.expression.ParserContext;

/**
 * ParserContext used to determine if a expression is a template
 * 
 * @author xinufo
 *
 */
public class DynamicParserContext implements ParserContext {
    private static final Pattern PATTERN = Pattern.compile("\\$\\{.+\\}");
    private String expression;

    public DynamicParserContext(String expression) {
        this.expression = expression;
    }

    @Override
    public boolean isTemplate() {
        return isTemplate(expression);
    }

    @Override
    public String getExpressionPrefix() {
        return "${";
    }

    @Override
    public String getExpressionSuffix() {
        return "}";
    }

    public static boolean isTemplate(String exp) {
        return PATTERN.matcher(exp).find();
    }

}
