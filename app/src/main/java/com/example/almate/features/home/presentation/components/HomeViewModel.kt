package com.example.almate.features.home.presentation.components

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.almate.data.model.GetGpaResponse
import com.example.almate.data.model.SupabaseUser
import com.example.almate.features.home.data.repository.SupabaseUserRepository
import com.example.almate.data.repository.UserPreferencesRepository
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
    data object Loading : HomeState
    data class Success(
        val homeData: HomeData,
        val isRefreshing: Boolean
    ) : HomeState
    data object Error : HomeState
}

data class HomeData(
    val supabaseUser: SupabaseUser,
    val gpaResponse: GetGpaResponse,
    val grades: List<GetGradesResponseItem>,
    val sortType: SubjectSortType
)

enum class SubjectSortType {
    ALPHABET, PERCENTAGE, WEIGHT, TEACHER
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val almaRepository: AlmaRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val supabaseRepository: SupabaseRepository,
    private val supabaseUserRepository: SupabaseUserRepository
): ViewModel() {

    var homeState: HomeState by mutableStateOf(HomeState.Loading)

    var supabaseUser: SupabaseUser by mutableStateOf(SupabaseUser("", "", 0, "", "", ""))
    var gpaResponse: GetGpaResponse by mutableStateOf(GetGpaResponse("", ""))
    var grades: List<GetGradesResponseItem> by mutableStateOf(emptyList())
    var showSortBottomSheet: Boolean by mutableStateOf(false)

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
                        supabaseUserRepository.upsertSupabaseUser(supabaseUser)
                    }
                    async {
                        gpaResponse = almaRepository.getGpa(credentials)
                    }
                    async {
                        grades = almaRepository.getGrades(credentials).sortedBy { it.name }
                    }
                }
                homeState = HomeState.Success(HomeData(supabaseUser, gpaResponse, grades, SubjectSortType.ALPHABET), false)
            } catch (e: Exception) {
                homeState = HomeState.Error
            }
        }
    }

    fun refresh() {
        val currentState = homeState
        if (currentState !is HomeState.Success) {
            return
        }
        viewModelScope.launch {
            homeState = currentState.copy(isRefreshing = true)
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
                homeState = HomeState.Success(HomeData(supabaseUser, gpaResponse, grades, SubjectSortType.ALPHABET), false)
            } catch (e: Exception) {
                homeState = HomeState.Error
            }
        }
    }

    fun sortBy(type: SubjectSortType) {
        val currentState = homeState
        if (currentState !is HomeState.Success) {
            return
        }
        homeState = when (type) {
           SubjectSortType.ALPHABET -> {
               currentState.copy(
                   homeData = currentState.homeData.copy(
                       grades = currentState.homeData.grades.sortedByDescending { it.name },
                       sortType = SubjectSortType.ALPHABET
                   )
               )
           }
           SubjectSortType.WEIGHT -> {
               currentState.copy(
                   homeData = currentState.homeData.copy(
                       grades = currentState.homeData.grades.sortedByDescending { it.weight },
                       sortType = SubjectSortType.WEIGHT
                   )
               )
           }
           SubjectSortType.PERCENTAGE -> {
               currentState.copy(
                   homeData = currentState.homeData.copy(
                       grades = sortGradesByPercent(currentState.homeData.grades),
                       sortType = SubjectSortType.PERCENTAGE
                   )
               )
           }
           SubjectSortType.TEACHER -> {
               currentState.copy(
                   homeData = currentState.homeData.copy(
                       grades = currentState.homeData.grades.sortedByDescending { it.teacher },
                       sortType = SubjectSortType.TEACHER
                   )
               )
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
