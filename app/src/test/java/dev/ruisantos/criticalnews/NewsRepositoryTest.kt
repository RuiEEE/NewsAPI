package dev.ruisantos.criticalnews

import dev.ruisantos.criticalnews.data.NewsRepositoryImpl
import dev.ruisantos.criticalnews.network.Article
import dev.ruisantos.criticalnews.network.FakeCriticalNewsDataSource
import dev.ruisantos.criticalnews.network.NewsSource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Instant

class NewsRepositoryTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: NewsRepositoryImpl

    @Before
    fun setup() {
        repository = NewsRepositoryImpl(FakeCriticalNewsDataSource(mixedArticles))
    }

    @Test
    fun `Verify news are sorted ascending by published date`() = runTest {
        val articles = repository.getNews().first()

        for(i in 0 until articles.size - 1) {
            val currentArticle = articles[i]
            val nextArticle = articles[i + 1]
            val currentPublishedAt = Instant.parse(currentArticle.publishedAt)
            val nextPublishedAt = Instant.parse(nextArticle.publishedAt)

            assertEquals(true, currentPublishedAt <= nextPublishedAt)
        }
    }
}

private val mixedArticles = listOf(
    Article(
        source = NewsSource(
            id = "cnn",
            name = "CNN"
        ),
        title = "Dianne Feinstein returning to Washington on Tuesday | CNN Politics",
        description = "Democratic Sen. Dianne Feinstein of California, who has been away from the Senate since February while recovering from shingles, will return to Washington on Tuesday, according to a spokesperson.",
        content = "Democratic Sen. Dianne Feinstein of California, who has been away from the Senate since February while recovering from shingles, will return to Washington on Tuesday, according to a spokesperson. \\r\\nS… [+2554 chars]",
        url = "https://www.cnn.com/2023/05/09/politics/dianne-feinstein-returns-to-washington/index.html",
        publishedAt = "2023-05-09T19:32:09Z",
        urlToImage = "https://media.cnn.com/api/v1/images/stellar/prod/221102154905-02-dianne-feinstein-file-0623.jpg?c=16x9&q=w_800,c_fill"
    ),
    Article(
        source = NewsSource(
            id = "cnn",
            name = "CNN"
        ),
        title = "Opinion: For my audacity in reporting a rape, I was sentenced to life in a box | CNN",
        description = "E. Jean Carroll trial against Trump brings up the trauma I experienced testifying in my rape case. It wasn’t the rape that did me in. It was testifying in court that broke me, writes Elizabeth Grey.",
        content = "Editors Note: Elizabeth Grey is a writer finishing her first book, a memoir on addiction. The views expressed in this commentary are the authors own. View more opinion on CNN.\\r\\nDont read it, I tell m… [+7195 chars]",
        url = "https://www.cnn.com/2023/05/09/opinions/e-jean-carroll-verdict-trump-witness-stand-grey/index.html",
        publishedAt = "2023-05-09T19:05:42.499Z",
        urlToImage = "https://media.cnn.com/api/v1/images/stellar/prod/230502095251-03-sketch-trump-e-jean-carroll-lawsuit.jpg?c=16x9&q=w_800,c_fill"
    ),
    Article(
        source = NewsSource(
            id = "cnn",
            name = "CNN"
        ),
        title = "CNBC parts ways with anchor who filed sexual harassment claim against former NBCUniversal CEO | CNN Business",
        description = "CNBC said on Tuesday that it will part ways with Hadley Gamble, the anchor and senior international correspondent who filed a sexual harassment complaint that led to the firing of NBC Universal chief executive Jeff Shell.",
        content = "CNBC said on Tuesday that it will part ways with Hadley Gamble, the anchor and senior international correspondent who filed a sexual harassment complaint that led to the firing of NBC Universal chief… [+760 chars]",
        url = "https://www.cnn.com/2023/05/09/media/cnbc-hadley-gamble/index.html",
        publishedAt = "2023-05-09T18:34:41Z",
        urlToImage = "https://media.cnn.com/api/v1/images/stellar/prod/230509142952-hadley-gamble-cnbc-file-041518.jpg?c=16x9&q=w_800,c_fill"
    )
)