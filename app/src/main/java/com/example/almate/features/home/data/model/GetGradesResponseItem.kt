package com.example.almate.features.home.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GetGradesResponseItem(
    val letter: String,
    val name: String,
    val path: String,
    val percent: String,
    val teacher: String,
    val weight: String
)