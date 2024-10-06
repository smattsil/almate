package com.example.almate.data.repository

import android.util.Log
import com.example.almate.data.model.GetOverallInfoResponse
import com.example.almate.data.model.SupabaseUser
import com.example.almate.domain.repository.SupabaseRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import java.io.IOException

class SupabaseRepositoryImpl(
    private val supabaseClient: SupabaseClient
) : SupabaseRepository {

    override suspend fun insertUser(overallInfo: GetOverallInfoResponse) {
        try {
            supabaseClient.from("users").insert(
                SupabaseUser(
                    username = overallInfo.username,
                    fullName = overallInfo.fullName,
                    overallInfo.rankedRating,
                    overallInfo.gpa,
                    profilePicture = "https://t3.ftcdn.net/jpg/05/16/27/58/360_F_516275801_f3Fsp17x6HQK0xQgDQEELoTuERO4SsWV.jpg",
                    overallInfo.lastUpdated
                )
            )
            Log.d("Supabase", "Successfully inserted user into the database.")
        } catch (e: Exception) {
            Log.e("Supabase", "Failed to insert user into the database. Either an error occurred or they already exist.")
        }
    }

    override suspend fun getUsers(): List<SupabaseUser> {
        return supabaseClient.from("users").select().decodeList<SupabaseUser>()
    }

    override suspend fun getUser(username: String): SupabaseUser {
        return supabaseClient.from("users").select {
            filter {
                eq("username", username)
            }
        }.decodeSingle<SupabaseUser>()
    }

    override suspend fun updateUser(username: String, overallInfo: GetOverallInfoResponse) {
        supabaseClient.from("users").update(
            {
                set("ranked_rating", overallInfo.rankedRating)
                set("gpa", overallInfo.gpa)
                set("last_updated", overallInfo.lastUpdated)
            }
        ) {
            filter {
                eq("username", username)
            }
        }
    }

    override suspend fun updateUserProfilePicture(username: String, profilePictureUrl: String) {
        supabaseClient.from("users").update(
            {
                set("profile_picture", profilePictureUrl)
            }
        ) {
            filter {
                eq("username", username)
            }
        }
    }

    override suspend fun deleteUser(username: String) {
        supabaseClient.from("users").delete {
            filter {
                eq("username", username)
            }
        }
    }

}
