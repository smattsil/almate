package com.example.almate.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GetGpaResponse(
    val cumulative: String,
    val current: String
)