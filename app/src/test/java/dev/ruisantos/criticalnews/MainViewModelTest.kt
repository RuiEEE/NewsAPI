package dev.ruisantos.criticalnews

import dev.ruisantos.criticalnews.data.FakeNewsRepository
import dev.ruisantos.criticalnews.ui.main.MainViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: MainViewModel
    private val fakeNewsRepository = FakeNewsRepository()

    @Before
    fun setup() {
        viewModel = MainViewModel(fakeNewsRepository)
    }

    @Test
    fun `Headline UI is shown after fetching the Headlines`() = runTest {
        viewModel.fetchHeadlines()
        assert(viewModel.ui.value is MainViewModel.UI.Headlines)
        assert((viewModel.ui.value as MainViewModel.UI.Headlines).articles.size == fakeNewsRepository.articles.size)
    }

    @Test
    fun `Initial state is headlines`() = runTest {
        assert(viewModel.ui.value is MainViewModel.UI.Headlines)
    }
}