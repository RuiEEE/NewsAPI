package dev.ruisantos.criticalnews.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@kotlinx.serialization.Serializable
data class HeadlineResponse(
    val articles: List<Article>
)

@kotlinx.serialization.Serializable
@Parcelize
data class Article(
    val source: NewsSource?,
    var title: String?,
    var description: String?,
    var url: String?,
    var urlToImage: String?,
    var content: String?,
    var publishedAt: String?,
) : Parcelable

@kotlinx.serialization.Serializable
@Parcelize
data class NewsSource(
    val id: String?,
    val name: String?
) : Parcelable