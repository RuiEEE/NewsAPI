package dev.ruisantos.criticalnews.network

import retrofit2.http.GET

interface NewsAPI {

    @GET("top-headlines")
    suspend fun headlines(): HeadlineResponse

}