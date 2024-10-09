package com.example.almate.features.home.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.almate.data.model.SupabaseUser

@Database(
    entities = [SupabaseUser::class],
    version = 1
)
abstract class SupabaseUserDatabase : RoomDatabase() {

    abstract fun dao(): SupabaseUserDao

}
