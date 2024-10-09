package com.example.almate.features.home.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.almate.data.model.SupabaseUser

@Dao
interface SupabaseUserDao {

    @Upsert
    suspend fun upsertSupabaseUser(supabaseUser: SupabaseUser)

    @Delete
    suspend fun deleteSupabaseUser(supabaseUser: SupabaseUser)

    @Query("SELECT * FROM supabaseuser LIMIT 1")
    suspend fun getSupabaseUser(): SupabaseUser

}
