package com.example.sushovon.contact.http;

import android.content.Context;
import android.util.Base64;
import android.widget.Toast;
import com.example.sushovon.contact.exception.RxErrorHandlingCallAdapterFactory;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by samik on 04/12/16.
 * Helper class to create the Retrofit service interface.
 */
public class ServiceGenerator {
    public static final String API_BASE_URL = "http://api.androidhive.info/";
    private static OkHttpClient.Builder _HttpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create());



    public static <S> S createService(Class<S> serviceClass, String username, String password, Context context) {
        try {
            if (username != null && password != null) {
                String credentials = username + ":" + password;
                final String basic =
                        "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

                /*TrustManagerBuilder tmb=
                        new TrustManagerBuilder().withManifestConfig(context);
                OkHttp3Integrator.applyTo(tmb, _HttpClient);*/

                _HttpClient.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Authorization", basic)
                                .header("Accept", "application/json")
                                .header("Content-Type", "application/json")
                                .method(original.method(), original.body());

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                });
            }
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            /*logging.setLevel(HttpLoggingInterceptor.Level.BODY);
             _HttpClient.hostnameVerifier((hostname, session) -> true);
            _HttpClient.addInterceptor(logging);*/
            OkHttpClient client = _HttpClient.build();//_HttpClient.sslSocketFactory(getSSLConfig(context).getSocketFactory()).build();
            Retrofit retrofit = builder.client(client).build();
            return retrofit.create(serviceClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}