package com.example.almate.di

import android.content.Context
import androidx.room.Room
import com.example.almate.data.remote.AlmateApi
import com.example.almate.data.repository.AlmaRepositoryImpl
import com.example.almate.data.repository.SupabaseRepositoryImpl
import com.example.almate.data.repository.UserPreferencesRepository
import com.example.almate.domain.repository.AlmaRepository
import com.example.almate.domain.repository.SupabaseRepository
import com.example.almate.features.home.data.room.SupabaseUserDao
import com.example.almate.features.home.data.room.SupabaseUserDatabase
import com.example.almate.features.profile.data.room.AttendancesDao
import com.example.almate.features.profile.data.room.AttendancesDatabase
import com.example.almate.features.profile.data.room.PersonalInfoDao
import com.example.almate.features.profile.data.room.PersonalInfoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    // supabase user cache
    @Provides
    @Singleton
    fun provideSupabaseUserDatabase(@ApplicationContext context: Context): SupabaseUserDatabase {
        return Room.databaseBuilder(context, SupabaseUserDatabase::class.java, "SupabaseUserDatabase")
            .build()
    }

    @Provides
    @Singleton
    fun provideSupabaseUserDao(supabaseUserDatabase: SupabaseUserDatabase): SupabaseUserDao {
        return supabaseUserDatabase.dao()
    }

    // personal info cache
    @Provides
    @Singleton
    fun providePersonalInfoDatabase(@ApplicationContext context: Context): PersonalInfoDatabase {
        return Room.databaseBuilder(context, PersonalInfoDatabase::class.java, "PersonalInfoDatabase")
            .build()
    }

    @Provides
    @Singleton
    fun  providePersonalInfoDao(personalInfoDatabase: PersonalInfoDatabase): PersonalInfoDao {
        return personalInfoDatabase.dao()
    }

    // attendances cache
    @Provides
    @Singleton
    fun provideAttendancesDatabase(@ApplicationContext context: Context): AttendancesDatabase {
        return Room.databaseBuilder(context, AttendancesDatabase::class.java, "AttendancesDatabase")
            .build()
    }

    @Provides
    @Singleton
    fun provideAttendancesDao(attendancesDatabase: AttendancesDatabase): AttendancesDao {
        return attendancesDatabase.dao()
    }

}
