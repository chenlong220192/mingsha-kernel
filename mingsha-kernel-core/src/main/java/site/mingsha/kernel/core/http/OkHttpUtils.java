package site.mingsha.kernel.core.http;

import java.security.cert.CertificateException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.*;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson2.JSON;
import site.mingsha.kernel.core.utils.ExceptionUtils;
import site.mingsha.kernel.logger.LogUtils;
import site.mingsha.kernel.logger.pattern.LogPattern;
import com.google.common.collect.Maps;

import okhttp3.*;

/**
 * Http请求工具类
 *
 */
public class OkHttpUtils {

    /**
     * JSON MediaType
     */
    private static final MediaType    MEDIATYPE_JSON = MediaType.parse("application/json;charset=utf-8");

    /**
     * OkHttpClient实例，忽略SSL证书验证
     */
    private final static OkHttpClient client         = getUnsafeOkHttpClient();

    /**
     * 创建忽略SSL证书验证的OkHttpClient
     *
     * @return OkHttpClient实例
     */
    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // 创建一个信任管理器，忽略所有的证书验证
            final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[] {};
                }
            } };

            // 安装信任管理器
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // 创建SSL socket factory
            final javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            // 创建OkHttpClient实例
            return new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    })
                    .connectTimeout(3_000, TimeUnit.MILLISECONDS)
                    .writeTimeout(3_000, TimeUnit.MILLISECONDS)
                    .readTimeout(3_000, TimeUnit.MILLISECONDS)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* --------------------------------------------- */

    /**
     * 通用HTTP请求方法
     *
     * @param url 请求地址
     * @param method HTTP方法 (GET, POST, PUT, DELETE等)
     * @param headers 自定义Header
     * @param body 请求体 (对于GET请求可以为null)
     * @return Response信息
     */
    public static String request(String url, String method, Map<String, String> headers, Object body) {
        final long start = System.currentTimeMillis();
        String result = StringUtils.EMPTY;
        try {
            RequestBody requestBody = null;
            if (body != null) {
                requestBody = RequestBody.create(MEDIATYPE_JSON, JSON.toJSONString(body));
            }

            Request.Builder requestBuilder = new Request.Builder().url(url);

            // 添加自定义Header
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    requestBuilder.addHeader(entry.getKey(), entry.getValue());
                }
            }

            // 设置HTTP方法
            switch (method.toUpperCase()) {
                case "GET":
                    requestBuilder.get();
                    break;
                case "POST":
                    if (requestBody != null) {
                        requestBuilder.post(requestBody);
                    }
                    break;
                case "PUT":
                    if (requestBody != null) {
                        requestBuilder.put(requestBody);
                    }
                    break;
                case "DELETE":
                    if (requestBody != null) {
                        requestBuilder.delete(requestBody);
                    } else {
                        requestBuilder.delete();
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported HTTP method: " + method);
            }

            Request request = requestBuilder.build();
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            LogUtils.error(String.format(LogPattern.ERROR_HTTP_NO_REQ, System.currentTimeMillis() - start, url, ExceptionUtils.extract(e)));
        }
        return result;
    }

    /* --------------------------------------------- */

    /**
     * Http Get请求
     *
     * @param url 请求地址
     * @param headers 自定义Header
     * @return Response信息
     */
    public static String get(String url, Map<String, String> headers) {
        final long start = System.currentTimeMillis();
        String result = StringUtils.EMPTY;
        try {
            Request.Builder requestBuilder = new Request.Builder().url(url).get();

            // 添加自定义Header
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    requestBuilder.addHeader(entry.getKey(), entry.getValue());
                }
            }

            Request request = requestBuilder.build();
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            LogUtils.error(String.format(LogPattern.ERROR_HTTP_NO_REQ, System.currentTimeMillis() - start, url, ExceptionUtils.extract(e)));
        }
        return result;
    }

    /**
     * Http Post请求
     *
     * @param obj 请求参数对象
     * @param url 请求地址
     * @param headers 自定义Header
     * @return Response信息
     */
    public static String post(Object obj, String url, Map<String, String> headers) {
        final long start = System.currentTimeMillis();
        String result = null;
        try {
            RequestBody body = RequestBody.create(MEDIATYPE_JSON, JSON.toJSONString(obj));
            Request.Builder requestBuilder = new Request.Builder().url(url).post(body);

            // 添加自定义Header
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    requestBuilder.addHeader(entry.getKey(), entry.getValue());
                }
            }

            Request request = requestBuilder.build();
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            LogUtils.error(String.format(LogPattern.ERROR_HTTP_NO_REQ, System.currentTimeMillis() - start, url, ExceptionUtils.extract(e)));
        }
        return result;
    }

    /* --------------------------------------------- */

    /**
     * Http Post请求
     *
     * @param obj 请求参数对象
     * @param url 请求地址
     * @return Response信息
     */
    public static String post(Object obj, String url) {
        return post(obj, url, Maps.newHashMap());
    }

    /**
     * Http Get请求
     *
     * @param url 请求地址
     * @return Response信息
     */
    public static String get(String url) {
        Map<String, String> headers = Maps.newHashMap();
        headers.put("Content-Type", MEDIATYPE_JSON.toString());
        return get(url, headers);
    }

}
