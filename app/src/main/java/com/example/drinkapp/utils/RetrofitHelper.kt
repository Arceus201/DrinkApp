package com.example.drinkapp.utils

import com.example.drinkapp.BuildConfig
import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.google.gson.GsonBuilder
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import javax.net.ssl.HostnameVerifier

object RetrofitHelper {
    // Ensure BASE_URL ends with "/" as required by Retrofit
    private val BASE_URL = if (BuildConfig.API_KEY_URL_BE.endsWith("/")) {
        BuildConfig.API_KEY_URL_BE
    } else {
        "${BuildConfig.API_KEY_URL_BE}/"
    }
    private const val TIMEOUT_SECONDS = 30L

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    private val authInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            // Add auth token here if needed
            // .addHeader("Authorization", "Bearer ${getToken()}")
            .build()
        chain.proceed(request)
    }

    /**
     * WARNING: This is for DEVELOPMENT ONLY!
     * Creates a trust manager that accepts all certificates (including self-signed).
     * DO NOT use this in production builds!
     */
    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier(HostnameVerifier { _, _ -> true })

            return builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private val okHttpClient = if (BuildConfig.DEBUG) {
        // Development: Accept self-signed certificates
        getUnsafeOkHttpClient()
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectionPool(ConnectionPool(5, 5, TimeUnit.MINUTES))
            .retryOnConnectionFailure(true)
            .build()
    } else {
        // Production: Use standard SSL validation
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectionPool(ConnectionPool(5, 5, TimeUnit.MINUTES))
            .retryOnConnectionFailure(true)
            .build()
    }

    private val gson = GsonBuilder()
        .setLenient()
        .serializeNulls()
        .create()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun getInstance(): Retrofit = retrofit
}