package com.example.almate.features.home.data.repository

import com.example.almate.features.home.data.model.GetGradesResponseItem
import com.example.almate.features.home.data.room.GradesDao
import javax.inject.Inject

class GradesRepository @Inject constructor(
    private val gradesDao: GradesDao
) {

    suspend fun upsertGrades(grades: List<GetGradesResponseItem>) {
        gradesDao.upsertGrades(grades)
    }

    suspend fun deleteGrades(grades: List<GetGradesResponseItem>) {
        gradesDao.deleteGrades(grades)
    }

    suspend fun getGrades(): List<GetGradesResponseItem> {
        return gradesDao.getGrades()
    }

}
