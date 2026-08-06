package com.taskachakov.notes.di

import android.content.Context
import androidx.room.Room
import com.taskachakov.notes.data.NotesDao
import com.taskachakov.notes.data.NotesDatabase
import com.taskachakov.notes.data.NotesRepositoryImpl
import com.taskachakov.notes.domain.NotesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface DataModule {

    @Singleton
    @Binds
    fun bindNotesRepository(
        impl: NotesRepositoryImpl
    ): NotesRepository

    companion object {
        @Singleton
        @Provides
        fun provideDatabase(
            @ApplicationContext context: Context
        ): NotesDatabase {
            return Room.databaseBuilder(
                context = context,
                klass = NotesDatabase::class.java,
                name = "notes.db"
            ).build()
        }


        @Singleton
        @Provides
        fun provideNotesDao(
            notesDatabase: NotesDatabase
        ): NotesDao {
            return notesDatabase.notesDao()
        }
    }
}