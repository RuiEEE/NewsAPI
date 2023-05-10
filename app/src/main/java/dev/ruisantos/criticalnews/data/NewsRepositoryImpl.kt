package dev.ruisantos.criticalnews.data

import dev.ruisantos.criticalnews.network.Article
import dev.ruisantos.criticalnews.network.CriticalNewsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepositoryImpl(private val retrofitCriticalNews: CriticalNewsDataSource) :
    NewsRepository {

    override fun getNews(): Flow<List<Article>> = flow {
        val articles = retrofitCriticalNews.getNews()
        emit(articles.sortedBy {
            it.publishedAt
        })
    }

}