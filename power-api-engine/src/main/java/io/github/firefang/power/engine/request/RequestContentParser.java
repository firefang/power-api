package io.github.firefang.power.engine.request;

import java.util.HashMap;
import java.util.Map;

import io.github.firefang.power.common.util.CollectionUtil;

/**
 * @author xinufo
 *
 */
public enum RequestContentParser {
    FORM {
        @SuppressWarnings("unchecked")
        @Override
        public Map<String, String> getExpressions(Object requestParams) {
            return (Map<String, String>) requestParams;
        }
    },
    BODY {
        @Override
        public Map<String, String> getExpressions(Object requestParams) {
            String p = (String) requestParams;
            Map<String, String> ret = new HashMap<>(CollectionUtil.mapSize(1));
            ret.put(this.name(), p);
            return ret;
        }
    },
    ARRAY {
        @Override
        public Map<String, String> getExpressions(Object requestParams) {
            String[] ps = (String[]) requestParams;
            Map<String, String> ret = new HashMap<>(CollectionUtil.mapSize(ps.length));
            for (int i = 0; i < ps.length; ++i) {
                ret.put(String.valueOf(i), ps[i]);
            }
            return ret;
        }
    };

    public abstract Map<String, String> getExpressions(Object requestParams);

}
