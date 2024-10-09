package com.example.almate.features.profile.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.almate.data.model.SupabaseUser
import com.example.almate.features.profile.data.model.GetAttendancesResponse
import com.example.almate.features.profile.data.model.GetPersonalInfoResponse

@Database(
    entities = [GetAttendancesResponse::class],
    version = 1
)
abstract class AttendancesDatabase : RoomDatabase() {

    abstract fun dao(): AttendancesDao

}
