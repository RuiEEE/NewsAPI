package dev.ruisantos.criticalnews.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dev.ruisantos.criticalnews.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class RetrofitCriticalNews {

    val okHttpCallFactory = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original: Request = chain.request()

            val builder: Request.Builder = original.newBuilder()
                .addHeader("X-Api-Key", BuildConfig.API_KEY)
                .method(original.method, original.body)
                .url(original.url.newBuilder().addQueryParameter("sources", BuildConfig.SOURCES).build())

            val request = builder.build()

            chain.proceed(request)
        }
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
        .build()

    val networkApi = Retrofit.Builder()
        .callFactory(okHttpCallFactory)
        .addConverterFactory(
            Json {
                ignoreUnknownKeys = true
            }.asConverterFactory("application/json".toMediaType())
        )
        .baseUrl("https://newsapi.org/v2/")
        .build()
        .create(NewsAPI::class.java)
}