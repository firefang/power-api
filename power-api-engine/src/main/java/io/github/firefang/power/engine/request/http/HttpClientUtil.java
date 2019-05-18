package io.github.firefang.power.engine.request.http;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;

/**
 * Utility class for sending a HTTP request with HttpCient
 * 
 * @author xinufo
 *
 */
public abstract class HttpClientUtil {
    private static PoolingHttpClientConnectionManager cm;

    private static final RequestConfig config = RequestConfig.custom()
            // 设置从连接池获取连接的等待超时时间
            .setConnectionRequestTimeout(1000)
            // 设置连接超时间
            .setConnectTimeout(1000)
            // 设置等待数据超时间时间
            .setSocketTimeout(5000).build();

    private static final CloseableHttpClient client = HttpClients.custom()
            // 设置默认请求配置
            .setDefaultRequestConfig(config)
            // 定期回收空闲连接
            .evictIdleConnections(500, TimeUnit.SECONDS)
            // 定期回收过期连接
            .evictExpiredConnections()
            // 连接存活时间，如果不设置，则根据长连接信息决定
            .setConnectionTimeToLive(500, TimeUnit.SECONDS)
            // 设置重试次数，默认是3次，当前是禁用掉
            .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
            // 设置连接池
            .setConnectionManager(cm).build();

    static {
        initConnectionManager();

        // 程序关闭时关闭连接池
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                destroy();
            }
        }, "httpclient-killer"));
    }

    public static CloseableHttpClient getClient() {
        return client;
    }

    public static void destroy() {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initConnectionManager() {
        SSLContext sslcontext;
        try {
            sslcontext = SSLContexts.custom().loadTrustMaterial(TrustAllStrategy.INSTANCE).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext, NoopHostnameVerifier.INSTANCE)).build();
        cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        cm.setMaxTotal(100);
        cm.setDefaultMaxPerRoute(20);
    }

    public static enum HttpRequestMethod {
        GET {
            @Override
            public HttpUriRequest getMethod(String uri) {
                return new HttpGet(uri);
            }
        },
        HEAD {
            @Override
            public HttpUriRequest getMethod(String uri) {
                return new HttpHead(uri);
            }
        },
        POST {
            @Override
            public HttpUriRequest getMethod(String uri) {
                return new HttpPost(uri);
            }
        },
        PUT {
            @Override
            public HttpUriRequest getMethod(String uri) {
                return new HttpPut(uri);
            }
        },
        DELETE {
            @Override
            public HttpUriRequest getMethod(String uri) {
                return new HttpDelete(uri);
            }
        },
        TRACE {
            @Override
            public HttpUriRequest getMethod(String uri) {
                return new HttpTrace(uri);
            }
        },
        OPTIONS {
            @Override
            public HttpUriRequest getMethod(String uri) {
                return new HttpOptions(uri);
            }
        };

        public abstract HttpUriRequest getMethod(String uri);
    }

}
