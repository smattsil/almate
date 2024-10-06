package com.example.almate.features.home.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val name: String,
    val percent: String,
    val weight: String
)