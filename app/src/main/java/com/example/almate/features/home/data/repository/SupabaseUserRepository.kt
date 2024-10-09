package com.example.almate.features.home.data.repository

import com.example.almate.data.model.SupabaseUser
import com.example.almate.features.home.data.room.SupabaseUserDao
import javax.inject.Inject

class SupabaseUserRepository @Inject constructor(
    private val supabaseUserDao: SupabaseUserDao
) {

    suspend fun upsertSupabaseUser(supabaseUser: SupabaseUser) {
        supabaseUserDao.upsertSupabaseUser(supabaseUser)
    }

    suspend fun deleteSupabaseUser(supabaseUser: SupabaseUser) {
        supabaseUserDao.deleteSupabaseUser(supabaseUser)
    }

    suspend fun getSupabaseUser(): SupabaseUser {
        return supabaseUserDao.getSupabaseUser()
    }

}
