package com.example.almate.features.profile.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.almate.data.model.SupabaseUser
import com.example.almate.data.repository.UserPreferencesRepository
import com.example.almate.domain.repository.AlmaRepository
import com.example.almate.domain.repository.SupabaseRepository
import com.example.almate.features.home.data.repository.SupabaseUserRepository
import com.example.almate.features.profile.data.model.GetAttendancesResponse
import com.example.almate.features.profile.data.model.GetPersonalInfoResponse
import com.example.almate.features.profile.data.repository.AttendancesRepository
import com.example.almate.features.profile.data.repository.PersonalInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ProfileState {
    data object Loading : ProfileState
    data class CachedSuccess(
        val profileData: ProfileData
    ) : ProfileState
    data class Success(
        val profileData: ProfileData,
        val isRefreshing: Boolean
    ) : ProfileState
    data object Error : ProfileState
}

data class ProfileData(
    val supabaseUser: SupabaseUser,
    val personalInfo: GetPersonalInfoResponse,
    val attendances: GetAttendancesResponse
)

@HiltViewModel
class ProfileViewModel @Inject constructor(

    private val userPreferencesRepository: UserPreferencesRepository,
    private val almaRepository: AlmaRepository,
    private val supabaseRepository: SupabaseRepository,

    private val supabaseUserRepository: SupabaseUserRepository,
    private val personalInfoRepository: PersonalInfoRepository,
    private val attendancesRepository: AttendancesRepository,

) : ViewModel() {

    var showConfirmDialog by mutableStateOf(false)
    var showUpdateProfilePictureDialog by mutableStateOf(false)

    var profileState: ProfileState by mutableStateOf(ProfileState.Loading)
    var supabaseUser: SupabaseUser? by mutableStateOf(null)
    var personalInfo: GetPersonalInfoResponse? by mutableStateOf(null)
    var attendances: GetAttendancesResponse? by mutableStateOf(null)

    init {
        try {
            fetchDataWithCache()
        } catch (e: Exception) {
            fetchData()
        }
    }

    fun fetchData() {
        viewModelScope.launch {
            try {
                coroutineScope {
                    val credentials = userPreferencesRepository.credentialsFlow.first()
                    async {
                        supabaseUser = supabaseRepository.getUser(credentials.username)
                        supabaseUserRepository.upsertSupabaseUser(supabaseUser!!)
                    }
                    async {
                        personalInfo = almaRepository.getPersonalInfo(credentials)
                        personalInfoRepository.upsertPersonalInfo(personalInfo!!)
                    }
                    async {
                        attendances = almaRepository.getAttendances(credentials)
                        attendancesRepository.upsertAttendances(attendances!!)
                    }
                }
                profileState = ProfileState.Success(ProfileData(supabaseUser!!, personalInfo!!, attendances!!), false)
            } catch (e: Exception) {
                profileState = ProfileState.Error
            }
        }
    }

    fun fetchDataWithCache() {
        viewModelScope.launch {
            profileState = ProfileState.Loading
            try {
                supabaseUser = supabaseUserRepository.getSupabaseUser()
                personalInfo = personalInfoRepository.getPersonalInfo()
                attendances = attendancesRepository.getAttendances()
                profileState = ProfileState.CachedSuccess(ProfileData(supabaseUser!!, personalInfo!!, attendances!!))
            } catch (e: Exception) {
                profileState = ProfileState.Loading
            }
        }
        fetchData()
    }

    fun refresh() {
        val currentState = profileState
        if (currentState !is ProfileState.Success) {
            return
        }
        viewModelScope.launch {
            profileState = currentState.copy(isRefreshing = true)
            try {
                coroutineScope {
                    val credentials = userPreferencesRepository.credentialsFlow.first()
                    async {
                        supabaseUser = supabaseRepository.getUser(credentials.username)
                        supabaseUserRepository.upsertSupabaseUser(supabaseUser!!)
                    }
                    async {
                        personalInfo = almaRepository.getPersonalInfo(credentials)
                        personalInfoRepository.upsertPersonalInfo(personalInfo!!)
                    }
                    async {
                        attendances = almaRepository.getAttendances(credentials)
                        attendancesRepository.upsertAttendances(attendances!!)
                    }
                }
                profileState = ProfileState.Success(ProfileData(supabaseUser!!, personalInfo!!, attendances!!), false)
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

    fun onPfpValueChange(newValue: String) {
        supabaseUser = supabaseUser!!.copy(profilePicture = newValue)
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