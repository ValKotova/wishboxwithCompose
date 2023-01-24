package com.valkotova.wishboxwithcompose.di

import android.content.Context
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.valkotova.wishboxwithcompose.BuildConfig
import com.valkotova.wishboxwithcompose.domain.useCases.prefs.GetTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import kotlin.math.min

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun provideJson(): Json =
        Json(Json) {
            ignoreUnknownKeys = true
            explicitNulls = false
        }

    @Singleton
    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context, getTokenUseCase: GetTokenUseCase): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
            .writeTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
        if (BuildConfig.DEBUG) {
            val interceptor =
                HttpLoggingInterceptor { message -> logLong("network", message, Log::w) }
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpBuilder.addInterceptor(interceptor)
            okHttpBuilder.addInterceptor(ChuckerInterceptor.Builder(context).build())
        }
        okHttpBuilder.hostnameVerifier { _, _ -> true }
        return okHttpBuilder
            .addInterceptor { chain ->
                chain.proceed(setUserAgent(chain.request(), getTokenUseCase.execute()))
            }
            .build()
    }

    private fun setUserAgent(original: Request, token: String): Request {
        return MyRequester(original)
            .appendHeader("Accept", "application/json") {
                original.header("Accept") == null
            }
            .appendHeader("Authorization", "Bearer $token") {
                token != ""
            }
            .build()
    }

    private class MyRequester(original: Request) {
        var builder: Request.Builder =
            original.newBuilder().method(original.method, original.body)

        fun appendHeader(name: String, value: String): MyRequester {
            return appendHeader(name, value) { true }
        }

        fun appendHeader(name: String, value: String, condition: () -> Boolean): MyRequester {
            if (condition.invoke()) {
                builder.addHeader(name, value)
            }
            return this
        }

        fun build(): Request {
            return builder.build()
        }
    }

    private fun logLong(tag: String, text: String, logFunction: (String, String) -> Any, part: Int? = null) {
        val textLength = text.length
        if (textLength > 100_000) {
            val textToPrint = text.substring(0, min(255, text.length))
            val message = "<very long text. L=$textLength> $textToPrint ..."
            logFunction.invoke(tag, message)
            return
        }

        val lineLength = 950
        val textToPrint = text.substring(0, min(lineLength, text.length))
        val message = part?.let { "p$it  $textToPrint" } ?: textToPrint

        logFunction.invoke(tag, message)
        if (textToPrint.length < text.length) {
            val clippedString = text.substring(lineLength)
            logLong(tag, clippedString, logFunction, part?.let { it + 1 } ?: 2)
        }
    }
}