package dev.ruisantos.criticalnews.ui.main

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.ruisantos.criticalnews.data.NewsRepository
import dev.ruisantos.criticalnews.network.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MainViewModel(val repository: NewsRepository) : ViewModel() {

    var hasAuthenticated = false

    private val _ui = MutableStateFlow<UI>(UI.Headlines(mutableListOf()))
    val ui: StateFlow<UI> = _ui

    fun fetchHeadlines() {
        hasAuthenticated = true
        viewModelScope.launch {
            repository.getNews().catch {
                it.printStackTrace()
            }.collect {
                _ui.emit(UI.Headlines(it))
            }
        }
    }

    suspend fun checkBiometricAvailability(context: Context) {
        if (hasAuthenticated) {
            return
        }

        val biometricManager = BiometricManager.from(context)
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> _ui.emit(UI.Authentication)
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> fetchHeadlines()
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> fetchHeadlines()
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> _ui.emit(UI.Settings)
        }
    }

    sealed class UI {
        class Headlines(val articles: List<Article>) : UI()
        object Authentication : UI()
        object Settings : UI()
    }

}

