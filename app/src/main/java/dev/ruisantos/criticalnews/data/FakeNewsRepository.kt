package dev.ruisantos.criticalnews.data

import dev.ruisantos.criticalnews.network.Article
import dev.ruisantos.criticalnews.network.NewsSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNewsRepository : NewsRepository {

    val articles = mutableListOf(
        Article(
            source = NewsSource(
                id = "cnn",
                name = "CNN"
            ),
            title = "Lorem Ipsum",
            description = "Big Lorem Ipsum",
            content = "Big Lorem Ipsum, but limited to 200 characters",
            url = "cnn.com",
            publishedAt = "now",
            urlToImage = "https://picsum.photos/200/300"
        )
    )

    override fun getNews(): Flow<List<Article>> = flow {
        emit(articles)
    }
}