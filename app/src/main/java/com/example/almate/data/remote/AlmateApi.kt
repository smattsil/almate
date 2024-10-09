package com.example.almate.data.remote

import com.example.almate.data.model.GetGpaResponse
import com.example.almate.data.model.GetOverallInfoResponse
import com.example.almate.features.auth.data.model.GetAuthenticityResponse
import com.example.almate.features.home.data.model.GetGradesResponseItem
import com.example.almate.features.home.data.model.GetSubjectResponse
import com.example.almate.features.profile.data.model.GetAttendancesResponse
import com.example.almate.features.profile.data.model.GetPersonalInfoResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface AlmateApi {

    @GET("authenticity")
    suspend fun getAuthenticity(
        @Header("school") school: String,
        @Header("username") username: String,
        @Header("password") password: String
    ): GetAuthenticityResponse

    @GET("grades")
    suspend fun getGrades(
        @Header("school") school: String,
        @Header("username") username: String,
        @Header("password") password: String
    ): List<GetGradesResponseItem>

    @GET("gpa")
    suspend fun getGpa(
        @Header("school") school: String,
        @Header("username") username: String,
        @Header("password") password: String
    ): GetGpaResponse

    @GET("subject")
    suspend fun getSubject(
        @Header("school") school: String,
        @Header("username") username: String,
        @Header("password") password: String,
        @Header("path") path: String
    ): GetSubjectResponse

    @GET("attendances")
    suspend fun getAttendances(
        @Header("school") school: String,
        @Header("username") username: String,
        @Header("password") password: String,
    ): GetAttendancesResponse

    @GET("personal-info")
    suspend fun getPersonalInfo(
        @Header("school") school: String,
        @Header("username") username: String,
        @Header("password") password: String,
    ): GetPersonalInfoResponse

    @GET("overall-info")
    suspend fun getOverallInfo(
        @Header("school") school: String,
        @Header("username") username: String,
        @Header("password") password: String
    ): GetOverallInfoResponse

}
