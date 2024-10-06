package com.example.almate.features.home.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetSubjectResponse(
    val assignments: List<Assignment>,
    val categories: List<Category>,
    val letter: String,
    val name: String,
    val percent: String,
    val teacher: String
)
