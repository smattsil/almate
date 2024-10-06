package com.example.almate.features.profile.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAttendancesResponse(
    val absences: String,
    val lates: String,
    val nottakens: String,
    val presents: String
)