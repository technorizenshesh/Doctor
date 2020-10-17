package com.technorizen.doctor.utils;

/*import android.content.Context;

import com.androidnetworking.interceptors.HttpLoggingInterceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;*/

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    /*public static String BASE_URL = AppConstant.BASE_URL;
    private static Retrofit retrofit = null;
    private static Retrofit retrofitWithHeader = null;

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static Retrofit getClientWithoutHeader(Context context) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = httpClient.addInterceptor(interceptor).connectTimeout(5, TimeUnit.MINUTES).
                readTimeout(100, TimeUnit.SECONDS).
                writeTimeout(100, TimeUnit.SECONDS)
                .build();
        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getUnsafeOkHttpClient().build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;

    }

    public static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return builder;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }*/

    public static String BASE_URL = AppConstant.BASE_URL;
    private static Retrofit retrofit = null;
    private static Retrofit retrofitWithHeader = null;
    private static SharedPreferences preferences;
    private static String access_token = "";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static Retrofit getClientWithoutHeader(Context context) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = httpClient.addInterceptor(interceptor).connectTimeout(5, TimeUnit.MINUTES).
                readTimeout(100, TimeUnit.SECONDS).
                writeTimeout(100, TimeUnit.SECONDS)
                .build();

        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;

    }

    public static Retrofit getClient(Context context) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder().addHeader("access-token", access_token);  // Request customization: add request headers
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = httpClient.addInterceptor(interceptor).connectTimeout(5, TimeUnit.MINUTES).
                readTimeout(100, TimeUnit.SECONDS).
                writeTimeout(100, TimeUnit.SECONDS).build();

        if (retrofitWithHeader == null) {
            retrofitWithHeader = new Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofitWithHeader;

    }

    public static String getStringBody(Response<ResponseBody> response) {

        BufferedReader reader = null;
        String output = null;
        try {

            if (response.body() != null) {
                reader = new BufferedReader(new InputStreamReader(response.body().byteStream()));
            } else if (response.errorBody() != null) {
                reader = new BufferedReader(new InputStreamReader(response.errorBody().byteStream()));
            }
            output = reader.readLine();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return output;
    }

}
