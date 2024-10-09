package com.example.almate.features.profile.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.almate.data.model.SupabaseUser
import com.example.almate.features.profile.data.model.GetPersonalInfoResponse

@Database(
    entities = [GetPersonalInfoResponse::class],
    version = 1
)
abstract class PersonalInfoDatabase : RoomDatabase() {

    abstract fun dao(): PersonalInfoDao

}
