package com.example.almate.features.home.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Assignment(
    val category: String,
    val date: String,
    val name: String,
    val percent: String
)