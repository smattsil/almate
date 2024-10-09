package com.example.almate.features.profile.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.almate.data.model.SupabaseUser
import com.example.almate.features.profile.data.model.GetAttendancesResponse
import com.example.almate.features.profile.data.model.GetPersonalInfoResponse

@Dao
interface AttendancesDao {

    @Upsert
    suspend fun upsertAttendances(attendances: GetAttendancesResponse)

    @Delete
    suspend fun deleteAttendances(attendances: GetAttendancesResponse)

    @Query("SELECT * FROM getattendancesresponse LIMIT 1")
    suspend fun getAttendances(): GetAttendancesResponse

}