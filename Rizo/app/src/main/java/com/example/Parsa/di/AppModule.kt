package com.example.Parsa.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.example.Parsa.data.datastore.dataStore
import com.example.Parsa.domain.repositories.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.example.Parsa.data.local.dao.AppDao
import com.example.Parsa.data.local.database.AppDatabase
import com.example.Parsa.domain.repositories.RepositoryImpl_DB
import com.example.Parsa.domain.repositories.Repository_DB
import com.example.Parsa.domain.repositories.SettingsRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> =
        context.dataStore

    @Provides
    @Singleton
    fun provideSettingsRepository(
        impl: SettingsRepositoryImpl
    ): SettingsRepository = impl

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideAppDao(database: AppDatabase): AppDao {
        return database.appDao()
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object RepositoryModule {

        @Provides
        @Singleton
        fun provideAppRepository(
            dao: AppDao
        ): Repository_DB = RepositoryImpl_DB(dao)
    }
}

