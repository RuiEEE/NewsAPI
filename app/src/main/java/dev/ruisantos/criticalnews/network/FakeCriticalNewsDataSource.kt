package dev.ruisantos.criticalnews.network

class FakeCriticalNewsDataSource(private val articles: List<Article> = mutableListOf()) : CriticalNewsDataSource {

    override suspend fun getNews(): List<Article> = articles

}