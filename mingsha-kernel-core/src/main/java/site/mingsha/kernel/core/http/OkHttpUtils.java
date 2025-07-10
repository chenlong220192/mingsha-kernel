package site.mingsha.kernel.core.http;

import okhttp3.*;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import com.alibaba.fastjson2.JSON;

/**
 * 现代风格 OkHttp 工具类，线程安全单例，支持同步/异步请求、JSON/表单、Header、超时等。
 * 推荐优先使用本类新API。
 *
 * @author mingsha
 * @date: 2025-07-10
 */
public class OkHttpUtils {
    private static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType FORM_TYPE = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build();
    private static final Executor ASYNC_EXECUTOR = Executors.newCachedThreadPool();

    private OkHttpUtils() {}

    /**
     * 同步GET请求
     * @param url 请求地址
     * @param headers 可选Header
     * @return OkHttp Response
     */
    public static Response get(String url, Map<String, String> headers) throws IOException {
        Request.Builder builder = new Request.Builder().url(url).get();
        if (headers != null) headers.forEach(builder::addHeader);
        return CLIENT.newCall(builder.build()).execute();
    }

    /**
     * 同步POST请求（JSON）
     * @param url 请求地址
     * @param bodyObj 请求体对象
     * @param headers 可选Header
     * @return OkHttp Response
     */
    public static Response postJson(String url, Object bodyObj, Map<String, String> headers) throws IOException {
        RequestBody body = RequestBody.create(JSON.toJSONString(bodyObj), JSON_TYPE);
        Request.Builder builder = new Request.Builder().url(url).post(body);
        if (headers != null) headers.forEach(builder::addHeader);
        return CLIENT.newCall(builder.build()).execute();
    }

    /**
     * 同步POST请求（表单）
     * @param url 请求地址
     * @param formMap 表单参数
     * @param headers 可选Header
     * @return OkHttp Response
     */
    public static Response postForm(String url, Map<String, String> formMap, Map<String, String> headers) throws IOException {
        FormBody.Builder form = new FormBody.Builder();
        if (formMap != null) formMap.forEach(form::add);
        Request.Builder builder = new Request.Builder().url(url).post(form.build());
        if (headers != null) headers.forEach(builder::addHeader);
        return CLIENT.newCall(builder.build()).execute();
    }

    /**
     * 同步PUT请求（JSON）
     */
    public static Response putJson(String url, Object bodyObj, Map<String, String> headers) throws IOException {
        RequestBody body = RequestBody.create(JSON.toJSONString(bodyObj), JSON_TYPE);
        Request.Builder builder = new Request.Builder().url(url).put(body);
        if (headers != null) headers.forEach(builder::addHeader);
        return CLIENT.newCall(builder.build()).execute();
    }

    /**
     * 同步DELETE请求（可带body）
     */
    public static Response deleteJson(String url, Object bodyObj, Map<String, String> headers) throws IOException {
        RequestBody body = bodyObj == null ? null : RequestBody.create(JSON.toJSONString(bodyObj), JSON_TYPE);
        Request.Builder builder = new Request.Builder().url(url).delete(body);
        if (headers != null) headers.forEach(builder::addHeader);
        return CLIENT.newCall(builder.build()).execute();
    }

    /**
     * 异步GET请求，返回CompletableFuture
     */
    public static CompletableFuture<Response> getAsync(String url, Map<String, String> headers) {
        return sendAsync(new Request.Builder().url(url).get(), headers);
    }

    /**
     * 异步POST请求（JSON），返回CompletableFuture
     */
    public static CompletableFuture<Response> postJsonAsync(String url, Object bodyObj, Map<String, String> headers) {
        RequestBody body = RequestBody.create(JSON.toJSONString(bodyObj), JSON_TYPE);
        return sendAsync(new Request.Builder().url(url).post(body), headers);
    }

    /**
     * 异步请求通用实现
     */
    private static CompletableFuture<Response> sendAsync(Request.Builder builder, Map<String, String> headers) {
        if (headers != null) headers.forEach(builder::addHeader);
        Request request = builder.build();
        CompletableFuture<Response> future = new CompletableFuture<>();
        CLIENT.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                future.completeExceptionally(e);
            }
            @Override
            public void onResponse(Call call, Response response) {
                future.complete(response);
            }
        });
        return future;
    }
}
