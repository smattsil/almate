package com.example.almate.features.home.presentation.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.almate.data.model.GetGpaResponse
import com.example.almate.data.model.SupabaseUser
import com.example.almate.data.repository.UserPreferencesRepository
import com.example.almate.domain.model.Credentials
import com.example.almate.domain.repository.AlmaRepository
import com.example.almate.domain.repository.SupabaseRepository
import com.example.almate.features.home.data.model.GetGradesResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface HomeState {
    object Loading : HomeState
    object Success : HomeState
    object Error : HomeState
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val almaRepository: AlmaRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val supabaseRepository: SupabaseRepository
): ViewModel() {

    var homeState: HomeState by mutableStateOf(HomeState.Loading)

    var supabaseUser: SupabaseUser by mutableStateOf(SupabaseUser("", "", 0, "", "", ""))
    var gpaResponse: GetGpaResponse by mutableStateOf(GetGpaResponse("", ""))
    var grades: List<GetGradesResponseItem> by mutableStateOf(emptyList())
    var sortedAlphabetically: Boolean = true

    var creds: Credentials? by mutableStateOf(null)

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            homeState = HomeState.Loading
            try {
                coroutineScope {
                    val credentials = userPreferencesRepository.credentialsFlow.first()
                    async {
                        supabaseUser = supabaseRepository.getUser(credentials.username)
                    }
                    async {
                        gpaResponse = almaRepository.getGpa(credentials)
                    }
                    async {
                        grades = almaRepository.getGrades(credentials).sortedBy { it.name }
                    }
                }
                homeState = HomeState.Success
            } catch (e: Exception) {
                homeState = HomeState.Error
            }
        }
    }

    suspend fun fetchCredentials(): Credentials {
        return viewModelScope.async {
            userPreferencesRepository.credentialsFlow.first()
        }.await()
    }

    fun switchSort() {
        sortedAlphabetically = !sortedAlphabetically
        grades = if (sortedAlphabetically) {
            grades.sortedBy { it.name }
        } else {
            sortGradesByPercent(grades)
        }
    }

    // this function was made my gemini.
    private fun sortGradesByPercent(grades: List<GetGradesResponseItem>): List<GetGradesResponseItem> {
        return grades.sortedWith(compareByDescending<GetGradesResponseItem> {
            it.percent.trim('%').toIntOrNull()
        }.thenBy {
            it.percent == "-"
        })
    }

}