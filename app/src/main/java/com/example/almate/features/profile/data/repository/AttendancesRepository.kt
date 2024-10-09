package com.example.almate.features.profile.data.repository

import com.example.almate.features.profile.data.model.GetAttendancesResponse
import com.example.almate.features.profile.data.room.AttendancesDao
import javax.inject.Inject

class AttendancesRepository @Inject constructor(
    private val attendancesDao: AttendancesDao
) {

    suspend fun upsertAttendances(attendances: GetAttendancesResponse) {
        attendancesDao.upsertAttendances(attendances)
    }

    suspend fun deleteAttendances(attendances: GetAttendancesResponse) {
        attendancesDao.deleteAttendances(attendances)
    }

    suspend fun getAttendances(): GetAttendancesResponse {
        return attendancesDao.getAttendances()
    }

}
