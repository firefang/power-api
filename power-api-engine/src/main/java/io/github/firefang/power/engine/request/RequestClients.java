package io.github.firefang.power.engine.request;

import io.github.firefang.power.engine.request.dubbo.DubboRequestClient;
import io.github.firefang.power.engine.request.http.HttpRequestClient;

/**
 * Registry of IRequestClient
 * 
 * @author xinufo
 *
 */
public enum RequestClients {

    HTTP {
        @Override
        public IRequestClient createClient() {
            return new HttpRequestClient();
        }
    },
    DUBBO {
        @Override
        public IRequestClient createClient() {
            return new DubboRequestClient();
        }
    };

    public abstract IRequestClient createClient();

}
