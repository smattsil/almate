package com.example.almate.features.home.data.repository

import com.example.almate.data.model.GetGpaResponse
import com.example.almate.data.model.SupabaseUser
import com.example.almate.features.home.data.room.GpaDao
import com.example.almate.features.home.data.room.SupabaseUserDao
import javax.inject.Inject

class GpaRepository @Inject constructor(
    private val gpaDao: GpaDao
) {

    suspend fun upsertGpa(getGpaResponse: GetGpaResponse) {
        gpaDao.upsertGpa(getGpaResponse)
    }

    suspend fun deleteGpa(getGpaResponse: GetGpaResponse) {
        gpaDao.deleteGpa(getGpaResponse)
    }

    suspend fun getGpa(): GetGpaResponse {
        return gpaDao.getGpa()
    }

}
