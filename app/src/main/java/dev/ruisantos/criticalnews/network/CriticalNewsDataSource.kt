package dev.ruisantos.criticalnews.network

interface CriticalNewsDataSource {

    suspend fun getNews(): List<Article>

}