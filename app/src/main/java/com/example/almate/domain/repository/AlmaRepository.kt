package com.example.almate.domain.repository

import com.example.almate.data.model.GetGpaResponse
import com.example.almate.data.model.GetOverallInfoResponse
import com.example.almate.domain.model.Credentials
import com.example.almate.features.auth.data.model.GetAuthenticityResponse
import com.example.almate.features.home.data.model.GetGradesResponse
import com.example.almate.features.home.data.model.GetGradesResponseItem
import com.example.almate.features.home.data.model.GetSubjectResponse
import com.example.almate.features.profile.data.GetAttendancesResponse
import com.example.almate.features.profile.data.GetPersonalInfoResponse

interface AlmaRepository {

    suspend fun getAuthenticity(
        credentials: Credentials
    ): GetAuthenticityResponse

    suspend fun getGrades(
        credentials: Credentials
    ): List<GetGradesResponseItem>

    suspend fun getGpa(
        credentials: Credentials
    ): GetGpaResponse

    suspend fun getSubject(
        credentials: Credentials,
        path: String
    ): GetSubjectResponse

    suspend fun getAttendances(
        credentials: Credentials
    ): GetAttendancesResponse

    suspend fun getPersonalInfo(
        credentials: Credentials
    ): GetPersonalInfoResponse

    suspend fun getOverallInfo(
        credentials: Credentials
    ): GetOverallInfoResponse

}
