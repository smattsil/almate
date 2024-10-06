package com.example.almate.domain.repository

import com.example.almate.data.model.GetOverallInfoResponse
import com.example.almate.data.model.SupabaseUser

interface SupabaseRepository {

    // insert, get, update, delete

    suspend fun insertUser(overallInfo: GetOverallInfoResponse): Unit

    suspend fun getUsers(): List<SupabaseUser>

    suspend fun getUser(username: String): SupabaseUser

    suspend fun updateUser(username: String, overallInfo: GetOverallInfoResponse): Unit

    suspend fun updateUserProfilePicture(username: String, profilePictureUrl: String): Unit

    suspend fun deleteUser(username: String): Unit


}
