package com.example.almate.features.profile.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.almate.data.model.SupabaseUser
import com.example.almate.features.profile.data.model.GetPersonalInfoResponse

@Dao
interface PersonalInfoDao {

    @Upsert
    suspend fun upsertPersonalInfo(personalInfo: GetPersonalInfoResponse)

    @Delete
    suspend fun deletePersonalInfo(personalInfo: GetPersonalInfoResponse)

    @Query("SELECT * FROM getpersonalinforesponse LIMIT 1")
    suspend fun getPersonalInfo(): GetPersonalInfoResponse

}