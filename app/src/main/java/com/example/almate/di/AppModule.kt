package com.example.almate.di

import android.app.Application
import com.example.almate.data.remote.AlmaApi
import com.example.almate.data.repository.AlmaRepositoryImpl
import com.example.almate.data.repository.SupabaseRepositoryImpl
import com.example.almate.data.repository.UserPreferencesRepository
import com.example.almate.domain.repository.AlmaRepository
import com.example.almate.domain.repository.SupabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
object AppModule {

    @Provides
    @Singleton
    fun provideAlmaApi(): AlmaApi {
        return Retrofit.Builder()
            .baseUrl("https://alma-api.onrender.com")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(AlmaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAlmaRepository(api: AlmaApi): AlmaRepository {
        return AlmaRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(app: Application): UserPreferencesRepository {
        return UserPreferencesRepository(app)
    }

    @Provides
    @Singleton
    fun providesSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = "https://urbhaorhwccexfatgxeo.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InVyYmhhb3Jod2NjZXhmYXRneGVvIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTcyNTY4MzMsImV4cCI6MjAzMjgzMjgzM30.KhP0InM7CxCE8tYSDRYU45MMxcPoUcMu4wqH7-eZORM"
        ) {
            install(Postgrest)
        }
    }

    @Provides
    @Singleton
    fun providesSupabaseRepository(client: SupabaseClient): SupabaseRepository {
        return SupabaseRepositoryImpl(client)
    }

}
