package com.binance.client.impl;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.binance.client.exception.BinanceApiException;
import com.binance.client.impl.utils.JsonWrapper;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

public class RestApiInvoker {

    private static final Logger log = LoggerFactory.getLogger(RestApiInvoker.class);
    private static  OkHttpClient client = new OkHttpClient();
    private static long REQUEST_TIMEOUT_MS = 2;


    public static void setProxy(String ip,int port){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //代理服务器的IP和端口号
        builder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port)));
        client = builder
                //设置读取超时时间
                .readTimeout(REQUEST_TIMEOUT_MS, TimeUnit.SECONDS)
                //设置写的超时时间
                .writeTimeout(REQUEST_TIMEOUT_MS, TimeUnit.SECONDS)
                .connectTimeout(REQUEST_TIMEOUT_MS, TimeUnit.SECONDS).build();
    }

    public static void setProxy(String ip,int port,String userName,String password){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //代理服务器的IP和端口号
        builder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port)));
        builder.proxyAuthenticator(new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                //设置代理服务器账号密码
                String credential = Credentials.basic(userName, password);
                return response.request().newBuilder()
                        .header("Proxy-Authorization", credential)
                        .build();
            }
        });
        client = builder
                //设置读取超时时间
                .readTimeout(REQUEST_TIMEOUT_MS, TimeUnit.SECONDS)
                //设置写的超时时间
                .writeTimeout(REQUEST_TIMEOUT_MS, TimeUnit.SECONDS)
                .connectTimeout(REQUEST_TIMEOUT_MS, TimeUnit.SECONDS).build();
    }

    static void checkResponse(JsonWrapper json) {
        try {
            if (json.containKey("success")) {
                boolean success = json.getBoolean("success");
                if (!success) {
                    String err_code = json.getStringOrDefault("code", "");
                    String err_msg = json.getStringOrDefault("msg", "");
                    if ("".equals(err_code)) {
                        throw new BinanceApiException(BinanceApiException.EXEC_ERROR, "[Executing] " + err_msg);
                    } else {
                        throw new BinanceApiException(BinanceApiException.EXEC_ERROR,
                                "[Executing] " + err_code + ": " + err_msg);
                    }
                }
            } else if (json.containKey("code")) {

                int code = json.getInteger("code");
                if (code != 200) {
                    String message = json.getStringOrDefault("msg", "");
                    throw new BinanceApiException(BinanceApiException.EXEC_ERROR,
                            "[Executing] " + code + ": " + message);
                }
            }
        } catch (BinanceApiException e) {
            throw e;
        } catch (Exception e) {
            throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR,
                    "[Invoking] Unexpected error: " + e.getMessage());
        }
    }

    static <T> T callSync(RestApiRequest<T> request) {
        try {
            String str;
            log.info("Request URL " + request.request.url());
            Response response = client.newCall(request.request).execute();
            // System.out.println(response.body().string());
            if (response != null && response.body() != null) {
                str = response.body().string();
                response.close();
            } else {
                throw new BinanceApiException(BinanceApiException.ENV_ERROR,
                        "[Invoking] Cannot get the response from server");
            }
            log.info("Response =====> " + str);
            JsonWrapper jsonWrapper = JsonWrapper.parseFromString(str);
            checkResponse(jsonWrapper);
            return request.jsonParser.parseJson(jsonWrapper);
        } catch (BinanceApiException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BinanceApiException(BinanceApiException.ENV_ERROR,
                    "[Invoking] Unexpected error: " + e.getMessage());
        }
    }

    static WebSocket createWebSocket(Request request, WebSocketListener listener) {
        return client.newWebSocket(request, listener);
    }

}
