package com.example.almate.features.home.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.almate.data.model.GetGpaResponse
import com.example.almate.data.model.SupabaseUser
import com.example.almate.features.home.data.model.GetGradesResponseItem

@Database(
    entities = [GetGradesResponseItem::class],
    version = 1
)
abstract class GradesDatabase : RoomDatabase() {

    abstract fun dao(): GradesDao

}
