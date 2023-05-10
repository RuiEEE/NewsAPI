package dev.ruisantos.criticalnews.data

import dev.ruisantos.criticalnews.network.Article
import dev.ruisantos.criticalnews.network.RetrofitCriticalNews
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepositoryImpl(private val retrofitCriticalNews: RetrofitCriticalNews) : NewsRepository {

    override fun getNews(): Flow<List<Article>> = flow {

        val articles = retrofitCriticalNews.networkApi.headlines().articles

        emit(articles.sortedBy {
            it.publishedAt
        })
    }

}