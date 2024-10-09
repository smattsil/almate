package com.example.almate.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class GetGpaResponse(
    @PrimaryKey
    val id: Int = 1,
    val cumulative: String,
    val current: String
)
