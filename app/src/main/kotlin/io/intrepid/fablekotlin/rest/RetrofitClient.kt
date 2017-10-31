package io.intrepid.fablekotlin.rest

import android.support.annotation.VisibleForTesting
import io.intrepid.fablekotlin.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private val BASE_URL = "https://fable-staging.herokuapp.com/"
    private val CONNECTION_TIMEOUT = 30L

    val restApi: RestApi by lazy { createRestApi(BASE_URL) }

    @VisibleForTesting
    internal fun getTestApi(baseUrl: String): RestApi = createRestApi(baseUrl)

    private fun createRestApi(baseUrl: String): RestApi {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.LOG_CONSOLE) {
            builder.addInterceptor(HttpLoggingInterceptor({
                Timber.v(it)
            }).setLevel(HttpLoggingInterceptor.Level.BODY))
        }

        builder.addInterceptor(Interceptor { chain ->
            val request = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/vnd.fable-server.com; version=1").build()
            chain.proceed(request)
        })

        val httpClient = builder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS).build()
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(RestApi::class.java)
    }
}
