package io.github.firefang.power.engine.request.http;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import io.github.firefang.power.common.util.EnumUtil;
import io.github.firefang.power.engine.request.IContentResolver;
import io.github.firefang.power.engine.request.IRequestBuilder;
import io.github.firefang.power.engine.request.IRequestClient;
import io.github.firefang.power.engine.request.IRequestInfo;
import io.github.firefang.power.engine.request.IResponseInfo;
import io.github.firefang.power.engine.request.http.HttpClientUtil.HttpRequestMethod;

/**
 * HTTP request client
 * 
 * @author xinufo
 *
 */
public class HttpRequestClient implements IRequestClient {
    private static final ResponseHandler<HttpResponseInfo> HANDLER = new PowerResponseHandler();
    private HttpClientContext httpContext;

    @Override
    public void init() {
        httpContext = HttpClientContext.create();
        httpContext.setCookieStore(new BasicCookieStore());
    }

    @Override
    public void connect(IRequestInfo request) {
    }

    @Override
    public void close() {
    }

    @Override
    public IResponseInfo request(IRequestInfo request) {
        HttpRequestInfo hri = (HttpRequestInfo) request;
        HttpUriRequest hr = EnumUtil.fromStringOptional(HttpRequestMethod.class, hri.getMethod())
                .map(hrm -> hrm.getMethod(hri.getUrl())).orElseThrow(RuntimeException::new);
        // set headers
        for (Map.Entry<String, String> e : hri.getHeaders().entrySet()) {
            hr.setHeader(e.getKey(), e.getValue());
        }
        Object params = hri.getParams();
        try {
            // set body
            if (hr instanceof HttpEntityEnclosingRequestBase) {
                HttpEntityEnclosingRequestBase he = (HttpEntityEnclosingRequestBase) hr;
                setParams(params, he);
            } else {
                if (params instanceof Map) {
                    // 重新生成请求对象以便将表单拼接到URL里
                    List<NameValuePair> list = map2list(params);
                    URIBuilder builder = new URIBuilder(hri.getUrl());
                    builder.setParameters(list);
                    String uri = builder.build().toString();
                    hr = EnumUtil.fromStringOptional(HttpRequestMethod.class, hri.getMethod())
                            .map(hrm -> hrm.getMethod(uri)).orElseThrow(RuntimeException::new);
                } else {
                    throw new IllegalArgumentException(hri.getMethod() + "不允许设置请求体");
                }
            }
            // request
            return HttpClientUtil.getClient().execute(hr, HANDLER, httpContext);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public IRequestBuilder newBuilder() {
        return new HttpRequestBuilder();
    }

    @Override
    public IContentResolver resolver(String type) {
        return EnumUtil.fromString(HttpContentType.class, type);
    }

    private void setParams(Object params, HttpEntityEnclosingRequestBase he) throws Exception {
        HttpEntity entity;
        if (params instanceof String) {
            entity = new StringEntity((String) params, "utf-8");
        } else {
            List<NameValuePair> list = map2list(params);
            entity = new UrlEncodedFormEntity(list);
        }
        he.setEntity(entity);
    }

    @SuppressWarnings("unchecked")
    private List<NameValuePair> map2list(Object params) {
        Map<String, String> map = (Map<String, String>) params;
        List<NameValuePair> list = map.entrySet().stream().map(e -> new BasicNameValuePair(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
        return list;
    }

    private static class PowerResponseHandler implements ResponseHandler<HttpResponseInfo> {

        @Override
        public HttpResponseInfo handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            HttpResponseInfo info = new HttpResponseInfo();
            // status
            info.setStatus(response.getStatusLine().getStatusCode());

            // headers
            Header[] headers = response.getAllHeaders();
            Map<String, String> headerMap = Arrays.stream(headers)
                    .collect(Collectors.toMap(Header::getName, Header::getValue));
            info.setHeaders(headerMap);

            // body
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                info.setBody(EntityUtils.toString(entity));
            }
            return info;
        }

    }

}
