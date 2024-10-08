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

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val almaRepository: AlmaRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val supabaseRepository: SupabaseRepository
): ViewModel() {

    val credentialsFlow = userPreferencesRepository.credentialsFlow
    var isLoggedIn: Boolean? by mutableStateOf(null)

    init {
        viewModelScope.launch {
            credentialsFlow.collect { credentials ->
                isLoggedIn = credentials.username.isNotBlank()
                if (isLoggedIn == true) {
                    val overallInfo = almaRepository.getOverallInfo(credentials)
                    try {
                        supabaseRepository.updateUser(credentials.username, overallInfo)
                    } catch (e: Exception) {
                    }
                }
            }
        }
    }

}
