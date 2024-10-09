package com.example.almate.features.home.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.almate.data.model.GetGpaResponse
import com.example.almate.data.model.SupabaseUser

@Database(
    entities = [GetGpaResponse::class],
    version = 1
)
abstract class GpaDatabase : RoomDatabase() {

    abstract fun dao(): GpaDao

}
