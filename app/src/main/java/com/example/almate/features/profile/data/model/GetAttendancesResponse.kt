package com.example.almate.features.profile.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class GetAttendancesResponse(
    @PrimaryKey
    val id: Int = 1,
    val absences: String,
    val lates: String,
    val nottakens: String,
    val presents: String
)
