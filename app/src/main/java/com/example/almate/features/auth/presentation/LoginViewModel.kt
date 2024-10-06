package com.example.almate.features.auth.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.almate.data.model.GetOverallInfoResponse
import com.example.almate.data.model.SupabaseUser
import com.example.almate.data.repository.UserPreferencesRepository
import com.example.almate.domain.model.Credentials
import com.example.almate.domain.repository.AlmaRepository
import com.example.almate.domain.repository.SupabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface LoginState {
    object Loading : LoginState
    object Success : LoginState
    object Error : LoginState
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val almaRepository: AlmaRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val supabaseRepository: SupabaseRepository
): ViewModel() {

    var credentials: Credentials by mutableStateOf(Credentials("", "", ""))
    var loginState: LoginState? by mutableStateOf(null)

    fun onSchoolChange(newSchool: String) {
        credentials = credentials.copy(school = newSchool)
    }

    fun onUsernameChange(newUsername: String) {
        credentials = credentials.copy(username = newUsername)
    }

    fun onPasswordChange(newPassword: String) {
        credentials = credentials.copy(password = newPassword)
    }

    init {
        viewModelScope.launch {
            credentials = userPreferencesRepository.credentialsFlow.first()
        }
    }

    fun login() {
        viewModelScope.launch {
            loginState = LoginState.Loading
            loginState = try {
                val response = almaRepository.getAuthenticity(credentials).authentic
                if (response) {
                    val overallInfo: GetOverallInfoResponse = almaRepository.getOverallInfo(credentials)
                    supabaseRepository.insertUser(overallInfo)
                    userPreferencesRepository.saveCredentials(credentials)
                    LoginState.Success
                } else {
                    LoginState.Error
                }
            } catch (e: Exception) {
                LoginState.Error
            }
        }
    }

}
