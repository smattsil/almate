package com.example.almate.features.profile.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class GetPersonalInfoResponse(
    @PrimaryKey
    val email: String,
    val familyNumber: String,
    val fullName: String,
    val lockerNumber: String
)
