package com.example.almate.features.home.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class GetGradesResponseItem(
    val letter: String,
    val name: String,
    @PrimaryKey
    val path: String,
    val percent: String,
    val teacher: String,
    val weight: String
)
