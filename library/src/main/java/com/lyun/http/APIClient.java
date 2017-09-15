package com.lyun.http;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.lyun.http.converter.APIConverterFactory;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * 简易APIClient
 * Created by ZHAOWEIWEI on 2016/12/14.
 */
public class APIClient {

    private static APIClient mInstance;
    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;

    private APIClient() {
    }

    private APIClient(String baseUrl, SSLSocketFactory sslSocketFactory, Interceptor tokenInterceptor, Interceptor interceptor) {
        // 初始化OkHttpClient
        mOkHttpClient = new OkHttpClient.Builder()
                // 忽略SSL验证
                // .sslSocketFactory(getSSLSocketFactory(), getTrustManager())
                .sslSocketFactory(sslSocketFactory, getTrustManager())
                .hostnameVerifier(getHostnameVerifier())
                .addInterceptor(tokenInterceptor)
                .addInterceptor(interceptor)
                .build();

        // 初始化Retrofit
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(APIConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClient)
                .build();
    }

    /**
     * 初始化APIClient
     *
     * @param baseUrl
     * @return
     */
    public static APIClient init(String baseUrl) {
        return init(baseUrl, getSSLSocketFactory(), null);
    }

    /**
     * 初始化APIClient
     *
     * @param baseUrl
     * @return
     */
    public static APIClient init(String baseUrl, SSLSocketFactory sslSocketFactory, Interceptor tokenInterceptor) {
        return init(baseUrl, sslSocketFactory, tokenInterceptor, null);
    }

    /**
     * 初始化APIClient
     *
     * @param baseUrl
     * @param interceptor
     * @return
     */
    public static APIClient init(String baseUrl, SSLSocketFactory sslSocketFactory, Interceptor tokenInterceptor, Interceptor interceptor) {
        if (mInstance == null) {
            synchronized (APIClient.class) {
                if (mInstance == null) {
                    mInstance = new APIClient(baseUrl, sslSocketFactory, tokenInterceptor, interceptor);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取APIClient实例
     *
     * @return
     */
    public static APIClient client() {
        return mInstance;
    }

    public Retrofit retrofit() {
        return mRetrofit;
    }

    public OkHttpClient okHttpClient() {
        return mOkHttpClient;
    }

    public static X509TrustManager getTrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws
                    CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws
                    CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    public static SSLSocketFactory getSSLSocketFactory() {
        SSLSocketFactory sslSocketFactory = new SSLSocketFactory() {
            @Override
            public String[] getDefaultCipherSuites() {
                return new String[0];
            }

            @Override
            public String[] getSupportedCipherSuites() {
                return new String[0];
            }

            @Override
            public Socket createSocket(Socket socket, String s, int i, boolean b) throws IOException {
                return null;
            }

            @Override
            public Socket createSocket(String s, int i) throws IOException, UnknownHostException {
                return null;
            }

            @Override
            public Socket createSocket(String s, int i, InetAddress inetAddress, int i1) throws IOException, UnknownHostException {
                return null;
            }

            @Override
            public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
                return null;
            }

            @Override
            public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress1, int i1) throws IOException {
                return null;
            }
        };
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new X509TrustManager[]{getTrustManager()}, new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sslSocketFactory;
    }

    public HostnameVerifier getHostnameVerifier() {
        return new AllowAllHostnameVerifier();
    }

}