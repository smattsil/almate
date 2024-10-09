package com.example.almate.features.home.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.almate.data.model.GetGpaResponse
import com.example.almate.data.model.SupabaseUser

@Dao
interface GpaDao {

    @Upsert
    suspend fun upsertGpa(gpa: GetGpaResponse)

    @Delete
    suspend fun deleteGpa(gpa: GetGpaResponse)

    @Query("SELECT * FROM getgparesponse LIMIT 1")
    suspend fun getGpa(): GetGpaResponse

}
