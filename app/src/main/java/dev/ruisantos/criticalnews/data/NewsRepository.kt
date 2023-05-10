package dev.ruisantos.criticalnews.data

import dev.ruisantos.criticalnews.network.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNews(): Flow<List<Article>>

}