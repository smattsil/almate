package com.example.almate.features.profile.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetPersonalInfoResponse(
    val email: String,
    val familyNumber: String,
    val fullName: String,
    val lockerNumber: String
)