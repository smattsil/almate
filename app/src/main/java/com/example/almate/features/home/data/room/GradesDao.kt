package com.example.almate.features.home.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.almate.data.model.GetGpaResponse
import com.example.almate.data.model.SupabaseUser
import com.example.almate.features.home.data.model.GetGradesResponseItem

@Dao
interface GradesDao {

    @Upsert
    suspend fun upsertGrades(grades: List<GetGradesResponseItem>)

    @Delete
    suspend fun deleteGrades(grades: List<GetGradesResponseItem>)

    @Query("SELECT * FROM getgradesresponseitem")
    suspend fun getGrades(): List<GetGradesResponseItem>

}
