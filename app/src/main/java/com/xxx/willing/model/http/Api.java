package com.xxx.willing.model.http;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.xxx.willing.ConfigClass;
import com.xxx.willing.base.App;

import java.io.File;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    private static ApiService service;

    private static final Object SYC = new Object();   //被锁的对象

    public static ApiService getInstance() {
        if (service == null) {
            synchronized (SYC) {
                if (service == null) {
                    Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl(ConfigClass.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(getOkHttpClient());
                    service = builder.build().create(ApiService.class);
                }
            }
        }
        return service;
    }

    /**
     * 添加OkHttp3
     */
    private static OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .cache(new Cache(App.getContext().getCacheDir(), ConfigClass.CACHE_SIZE))
                .sslSocketFactory(createSSLSocketFactory())
                .connectTimeout(ConfigClass.HTTP_TIME_OUT, TimeUnit.MILLISECONDS)
                .readTimeout(ConfigClass.HTTP_TIME_OUT, TimeUnit.MILLISECONDS)
                .writeTimeout(ConfigClass.HTTP_TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new ApiIntercept()).build();
    }

    /**
     * 添加HttpS 证书
     *
     * @return 通用Https 证书
     */
    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception ignored) {
            Log.e("sslError", "证书过期");
        }
        return ssfFactory;
    }

    /**
     * 获取上传文件的请求体
     */
    public static MultipartBody.Part getFileRequestBody(String fileName) {
        File file = new File(fileName);
        RequestBody requestBody = RequestBody.create(MediaType.parse("file/*"), file);
        return MultipartBody.Part.createFormData("file", file.getName(), requestBody);
    }

    public static class TrustAllCerts implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
            //统一跳过验证
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
            //统一跳过验证
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];

        }
    }
}
