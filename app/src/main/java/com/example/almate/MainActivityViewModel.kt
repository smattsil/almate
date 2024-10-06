package com.example.almate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.almate.data.repository.UserPreferencesRepository
import com.example.almate.domain.repository.AlmaRepository
import com.example.almate.domain.repository.SupabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface AppState {
    data object Loading : AppState
    data object Main : AppState
    data object Auth : AppState
    data object Error : AppState
}

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val almaRepository: AlmaRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val supabaseRepository: SupabaseRepository
): ViewModel() {

    val credentialsFlow = userPreferencesRepository.credentialsFlow
    var appState: AppState by mutableStateOf(AppState.Loading)
    var isLoggedIn: Boolean by mutableStateOf(false)

    init {
        viewModelScope.launch {
            credentialsFlow.collect { credentials ->
                val isLoggedIn = credentials.username.isNotBlank()
                if (isLoggedIn) {
                    val overallInfo = almaRepository.getOverallInfo(credentials)
                    try {
                        supabaseRepository.updateUser(credentials.username, overallInfo)
                        appState = AppState.Main
                    } catch (e: Exception) {
                        appState = AppState.Error
                    }
                }
            }
        }
    }

}