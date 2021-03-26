package com.project.prediction_application.dagger

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.project.prediction_application.BuildConfig
import com.project.prediction_application.common_helper.PreferenceHelper
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class CreditCardDetailsModule(val context: Context?, private val baseUrl: String) {
    @Provides
    @Singleton
    internal fun provideInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            var request: Request? = null
            try {

                val sessionManager = PreferenceHelper(context!!)
                val authorizationKey =
                    sessionManager.getString(PreferenceHelper.Key.AuthorizationKey.name)
                request = if (authorizationKey.isEmpty()) {
                    chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .method(original.method, original.body)
                        .build()
                } else {
                    chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", authorizationKey)
                        .addHeader("x-api-key", authorizationKey)
                        .method(original.method, original.body)
                        .build()
                }

            } catch (authFailureError: Exception) {
                authFailureError.printStackTrace()
            }
            chain.proceed(request!!)
        }
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {

        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.interceptors().add(interceptor)

        okHttpBuilder.readTimeout(60, TimeUnit.SECONDS)
        okHttpBuilder.connectTimeout(60, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            okHttpBuilder.interceptors().add(logging)
        }

        return okHttpBuilder.build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl)
            // .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

}