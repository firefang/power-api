package io.github.firefang.power.engine.expression.keyword.impl;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import io.github.firefang.power.common.util.CollectionUtil;
import io.github.firefang.power.engine.EngineConstants;
import io.github.firefang.power.engine.expression.keyword.IKeywordClassProvider;
import io.github.firefang.power.engine.expression.keyword.IKeywordFinder;
import io.github.firefang.power.engine.expression.keyword.Keyword;
import io.github.firefang.power.engine.util.JarLoaderUtil;

/**
 * Default implementation of IKeywordFinder using SPI
 * 
 * @author xinufo
 *
 */
public class SPIKeywordFinder implements IKeywordFinder {

    @Override
    public Map<String, Method> find() {
        Map<String, Method> ret = new HashMap<>(CollectionUtil.MAP_DEFAULT_SIZE);
        URLClassLoader loader = JarLoaderUtil.getClassLoader(EngineConstants.NAME_KEYWORD_LOADER);
        ServiceLoader<IKeywordClassProvider> providers = ServiceLoader.load(IKeywordClassProvider.class, loader);

        List<Class<?>> classes = new LinkedList<>();

        for (IKeywordClassProvider provider : providers) {
            classes.addAll(provider.keywordClasses());
        }

        for (Class<?> clazz : classes) {
            collectKeyword(clazz, ret);
        }
        return ret;
    }

    private void collectKeyword(Class<?> clazz, Map<String, Method> result) {
        for (Method m : clazz.getDeclaredMethods()) {
            Keyword k = m.getAnnotation(Keyword.class);
            if (k != null && Modifier.isStatic(m.getModifiers())) {
                String name = k.value();
                if (!isLegalIdentifier(name)) {
                    throw new IllegalArgumentException("Keyword name must be a legal java identifier");
                }
                result.put(name, m);
            }
        }
    }

    private static boolean isLegalIdentifier(String input) {
        if (input.length() > 0) {
            char[] chars = input.toCharArray();
            if (Character.isJavaIdentifierStart(chars[0])) {
                for (int i = 1; i < chars.length; ++i) {
                    if (!Character.isJavaIdentifierPart(chars[i])) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

}
