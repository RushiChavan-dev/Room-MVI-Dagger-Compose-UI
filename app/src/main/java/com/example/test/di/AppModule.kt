package com.example.test.di

import android.app.Application
import androidx.room.Room
import com.example.test.data.local.NoteDatabase
import com.example.test.data.repository.NoteRepository
import com.example.test.domain.repository.NoteRepositoryImpl
import com.example.test.domain.use_case.DeleteNoteUseCase
import com.example.test.domain.use_case.GetAllNotesUseCase
import com.example.test.domain.use_case.GetNoteByIdUseCase
import com.example.test.domain.use_case.InsertNoteUseCase
import com.example.test.domain.use_case.NoteUseCases

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            "note_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(database: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(database.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getAllNotesUseCase = GetAllNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            getNoteByIdUseCase = GetNoteByIdUseCase(repository),
            insertNoteUseCase = InsertNoteUseCase(repository)
        )
    }

}