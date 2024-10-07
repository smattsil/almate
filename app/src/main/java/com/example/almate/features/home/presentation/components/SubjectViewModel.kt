package com.example.almate.features.home.presentation.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.almate.data.repository.UserPreferencesRepository
import com.example.almate.domain.repository.AlmaRepository
import com.example.almate.features.home.data.model.GetGradesResponseItem
import com.example.almate.features.home.data.model.GetSubjectResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SubjectState {
    object Loading : SubjectState
    object Success : SubjectState
    object Error : SubjectState
}

enum class AssignmentSortType {
    ALPHABET, CATEGORY, PERCENT, DATE
}

@HiltViewModel
class SubjectViewModel @Inject constructor(
    private val almaRepository: AlmaRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    var path: String? by mutableStateOf(null)
    var subjectState: SubjectState by mutableStateOf(SubjectState.Loading)
    var subjectResponse: GetSubjectResponse by mutableStateOf(GetSubjectResponse(emptyList(), emptyList(), "", "", "", ""))
    var sortType: AssignmentSortType by mutableStateOf(AssignmentSortType.ALPHABET)
    var showSortBottomSheet: Boolean by mutableStateOf(false)

    init {
        if (path != null) {
            fetchData(path!!)
        }
    }

    fun fetchData(path: String) {
        viewModelScope.launch {
            subjectState = SubjectState.Loading
            clearSubjectResponse()
            try {
                val credentials = userPreferencesRepository.credentialsFlow.first()
                val response = almaRepository.getSubject(credentials, path)
                subjectResponse = response.copy(assignments = response.assignments.sortedByDescending { it.name })
                subjectState = SubjectState.Success
            } catch (e: Exception) {
                subjectState = SubjectState.Error
            }
        }
    }

    fun clearSubjectResponse() {
        subjectResponse = GetSubjectResponse(emptyList(), emptyList(), "", "", "", "")
    }

    fun sortBy(type: AssignmentSortType) {
        subjectResponse = when (type) {
            AssignmentSortType.ALPHABET -> {
                sortType = AssignmentSortType.ALPHABET
                subjectResponse.copy(assignments = subjectResponse.assignments.sortedByDescending { it.name })
            }
            AssignmentSortType.CATEGORY -> {
                sortType = AssignmentSortType.CATEGORY
                subjectResponse.copy(assignments = subjectResponse.assignments.sortedByDescending { it.category })
            }
            AssignmentSortType.PERCENT -> {
                sortType = AssignmentSortType.PERCENT
                subjectResponse.copy(assignments = subjectResponse.assignments.sortedByDescending { it.percent })
            }
            AssignmentSortType.DATE -> {
                sortType = AssignmentSortType.DATE
                subjectResponse.copy(assignments = subjectResponse.assignments.sortedByDescending { it.date })
            }
        }
    }

}