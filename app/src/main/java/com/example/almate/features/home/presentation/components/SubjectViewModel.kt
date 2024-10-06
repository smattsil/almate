package com.example.almate.features.home.presentation.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.almate.data.repository.UserPreferencesRepository
import com.example.almate.domain.repository.AlmaRepository
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

enum class SortType {
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
                println("POOP: got creds")

                subjectResponse = almaRepository.getSubject(credentials, path)
                println("POOP: got resp")

                subjectState = SubjectState.Success

            } catch (e: Exception) {
                subjectState = SubjectState.Error
            }
        }
    }

    fun clearSubjectResponse() {
        subjectResponse = GetSubjectResponse(emptyList(), emptyList(), "", "", "", "")
    }

}