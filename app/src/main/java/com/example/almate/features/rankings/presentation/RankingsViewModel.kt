package com.example.almate.features.rankings.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.almate.data.model.SupabaseUser
import com.example.almate.data.repository.UserPreferencesRepository
import com.example.almate.domain.model.Credentials
import com.example.almate.domain.repository.SupabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

sealed interface LeaderboardState {
    object Loading : LeaderboardState
    object Success : LeaderboardState
    object Error : LeaderboardState
}

@HiltViewModel
class RankingsViewModel @Inject constructor(
    private val supabaseRepository: SupabaseRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    var leaderboardState: LeaderboardState by mutableStateOf(LeaderboardState.Loading)
    var supabaseUsers: List<SupabaseUser> by mutableStateOf(emptyList())

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        viewModelScope.launch {
            try {
                leaderboardState = LeaderboardState.Loading
                supabaseUsers = supabaseRepository.getUsers().sortedByDescending { it.rankedRating }
                leaderboardState = LeaderboardState.Success
            } catch (e: IOException) {
                leaderboardState = LeaderboardState.Error
            }
        }
    }

}
