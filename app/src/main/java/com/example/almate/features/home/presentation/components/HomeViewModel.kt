package com.example.almate.features.home.presentation.components

import android.util.Log
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

enum class SubjectSortType {
    ALPHABET, PERCENTAGE, WEIGHT, TEACHER
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
    var showSortBottomSheet: Boolean by mutableStateOf(false)
    var sortType: SubjectSortType by mutableStateOf(SubjectSortType.ALPHABET)

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

    fun silentFetchData() {
        viewModelScope.launch {
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
            } catch (e: Exception) {
                Log.d("ALMATE", "Failed to silently fetch data.")
            }
        }
    }

    suspend fun fetchCredentials(): Credentials {
        return viewModelScope.async {
            userPreferencesRepository.credentialsFlow.first()
        }.await()
    }

    fun sortBy(type: SubjectSortType) {
        grades = when (type) {
           SubjectSortType.ALPHABET -> {
               sortType = SubjectSortType.ALPHABET
               grades.sortedByDescending { it.name }
           }
            SubjectSortType.WEIGHT -> {
                sortType = SubjectSortType.WEIGHT
                grades.sortedByDescending { it.weight }
            }
            SubjectSortType.PERCENTAGE -> {
                sortType = SubjectSortType.PERCENTAGE
                sortGradesByPercent(grades)
            }
            SubjectSortType.TEACHER -> {
                sortType = SubjectSortType.TEACHER
                grades.sortedByDescending { it.teacher }
            }
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

fun String.titleCase(): String {
    return this.split(" ").joinToString(" ") { it.lowercase().replaceFirstChar { char -> char.uppercase() } }
}
