package com.example.almate.features.profile.data.repository

import com.example.almate.features.profile.data.model.GetPersonalInfoResponse
import com.example.almate.features.profile.data.room.PersonalInfoDao
import javax.inject.Inject

class PersonalInfoRepository @Inject constructor(
    private val personalInfoDao: PersonalInfoDao
) {

    suspend fun upsertPersonalInfo(personalInfo: GetPersonalInfoResponse) {
        personalInfoDao.upsertPersonalInfo(personalInfo)
    }

    suspend fun deletePersonalInfo(personalInfo: GetPersonalInfoResponse) {
        personalInfoDao.deletePersonalInfo(personalInfo)
    }

    suspend fun getPersonalInfo(): GetPersonalInfoResponse {
        return personalInfoDao.getPersonalInfo()
    }

}
