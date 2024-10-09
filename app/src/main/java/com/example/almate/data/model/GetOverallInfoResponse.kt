package com.example.almate.data.model


import kotlinx.serialization.Serializable

@Serializable
data class GetOverallInfoResponse(
    val fullName: String,
    val gpa: String,
    val lastUpdated: String,
    val rankedRating: Int,
    val username: String
)
