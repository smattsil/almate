package com.example.almate.features.auth.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GetAuthenticityResponse(
    val authentic: Boolean
)
