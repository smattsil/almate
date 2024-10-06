package com.example.almate.data.repository

import com.example.almate.data.model.GetGpaResponse
import com.example.almate.data.model.GetOverallInfoResponse
import com.example.almate.data.remote.AlmaApi
import com.example.almate.domain.model.Credentials
import com.example.almate.domain.repository.AlmaRepository
import com.example.almate.features.auth.data.model.GetAuthenticityResponse
import com.example.almate.features.home.data.model.GetGradesResponse
import com.example.almate.features.home.data.model.GetGradesResponseItem
import com.example.almate.features.home.data.model.GetSubjectResponse
import com.example.almate.features.profile.data.GetAttendancesResponse
import com.example.almate.features.profile.data.GetPersonalInfoResponse

class AlmaRepositoryImpl(
    private val api: AlmaApi
): AlmaRepository {

    override suspend fun getAuthenticity(credentials: Credentials): GetAuthenticityResponse {
        return api.getAuthenticity(credentials.school, credentials.username, credentials.password)
    }

    override suspend fun getGrades(credentials: Credentials): List<GetGradesResponseItem> {
        return api.getGrades(credentials.school, credentials.username, credentials.password)
    }

    override suspend fun getGpa(credentials: Credentials): GetGpaResponse {
        return api.getGpa(credentials.school, credentials.username, credentials.password)
    }

    override suspend fun getSubject(credentials: Credentials, path: String): GetSubjectResponse {
        return api.getSubject(credentials.school, credentials.username, credentials.password, path)
    }

    override suspend fun getAttendances(credentials: Credentials): GetAttendancesResponse {
        return api.getAttendances(credentials.school, credentials.username, credentials.password)
    }

    override suspend fun getPersonalInfo(credentials: Credentials): GetPersonalInfoResponse {
        return api.getPersonalInfo(credentials.school, credentials.username, credentials.password)
    }

    override suspend fun getOverallInfo(credentials: Credentials): GetOverallInfoResponse {
        return api.getOverallInfo(credentials.school, credentials.username, credentials.password)
    }

}
