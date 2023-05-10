package dev.ruisantos.criticalnews.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.ruisantos.criticalnews.data.NewsRepository
import dev.ruisantos.criticalnews.network.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MainViewModel(val repository: NewsRepository) : ViewModel() {

    private val _ui = MutableStateFlow<UI>(UI.Headlines(mutableListOf()))
    val ui: StateFlow<UI> = _ui

    fun fetchHeadlines() {
        viewModelScope.launch {
            repository.getNews().catch {
                it.printStackTrace()
            }.collect {
                _ui.emit(UI.Headlines(it))
            }
        }
    }

    sealed class UI {
        class Headlines(val articles: List<Article>) : UI()
    }

}