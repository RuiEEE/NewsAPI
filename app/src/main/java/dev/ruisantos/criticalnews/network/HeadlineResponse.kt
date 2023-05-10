package dev.ruisantos.criticalnews.network

@kotlinx.serialization.Serializable
data class HeadlineResponse(
    val articles: List<Article>
)

@kotlinx.serialization.Serializable
data class Article(
    val source: NewsSource?,
    var title: String?,
    var description: String?
)

@kotlinx.serialization.Serializable
data class NewsSource(
    val id: String?,
    val name: String?
)