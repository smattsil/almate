package com.example.almate.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class SupabaseUser(
    @PrimaryKey
    @SerialName("username")
    val username: String,
    @SerialName("full_name")
    val fullName: String,
    @SerialName("ranked_rating")
    val rankedRating: Int,
    @SerialName("gpa")
    val gpa: String,
    @SerialName("profile_picture")
    val profilePicture: String,
    @SerialName("last_updated")
    val lastUpdated: String
)
