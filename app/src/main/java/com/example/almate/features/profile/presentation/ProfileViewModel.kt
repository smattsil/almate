package com.example.almate.features.profile.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.almate.data.model.SupabaseUser
import com.example.almate.data.repository.UserPreferencesRepository
import com.example.almate.domain.model.Credentials
import com.example.almate.domain.repository.AlmaRepository
import com.example.almate.domain.repository.SupabaseRepository
import com.example.almate.features.profile.data.GetAttendancesResponse
import com.example.almate.features.profile.data.GetPersonalInfoResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ProfileState {
    object Loading : ProfileState
    object Success : ProfileState
    object Error : ProfileState
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val almaRepository: AlmaRepository,
    private val supabaseRepository: SupabaseRepository
) : ViewModel() {

    var showConfirmDialog by mutableStateOf(false)
    var showUpdateProfilePictureDialog by mutableStateOf(false)

    var profileState: ProfileState by mutableStateOf(ProfileState.Loading)
    var supabaseUser: SupabaseUser? by mutableStateOf(null)
    var personalInfo: GetPersonalInfoResponse? by mutableStateOf(null)
    var attendances: GetAttendancesResponse? by mutableStateOf(null)

    var creds: Credentials? by mutableStateOf(null)

    init {
        fetchData()
    }

    fun onPfpValueChange(newValue: String) {
        supabaseUser = supabaseUser!!.copy(profilePicture = newValue)
    }

    fun fetchData() {
        viewModelScope.launch {
            profileState = ProfileState.Loading
            try {
                coroutineScope {
                    val credentials = userPreferencesRepository.credentialsFlow.first()
                    async {
                        supabaseUser = supabaseRepository.getUser(credentials.username)
                    }
                    async {
                        personalInfo = almaRepository.getPersonalInfo(credentials)
                    }
                    async {
                        attendances = almaRepository.getAttendances(credentials)
                    }
                }
                profileState = ProfileState.Success
            } catch (e: Exception) {
                profileState = ProfileState.Error
            }
        }
    }

    fun updateProfilePicture(profilePictureUrl: String) {
        viewModelScope.launch {
            val credentials = userPreferencesRepository.credentialsFlow.first()
            supabaseRepository.updateUserProfilePicture(credentials.username, profilePictureUrl)
        }
    }

    suspend fun fetchCredentials(): Credentials {
        return viewModelScope.async {
            userPreferencesRepository.credentialsFlow.first()
        }.await()
    }

    fun logout(removeFromDB: Boolean) {
        viewModelScope.launch {
            val credentials = userPreferencesRepository.credentialsFlow.first()
            if (removeFromDB) {
                supabaseRepository.deleteUser(credentials.username)
            }
            userPreferencesRepository.clearCredentials()
        }
    }

}