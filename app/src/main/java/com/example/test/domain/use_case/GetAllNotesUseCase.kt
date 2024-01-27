package com.example.test.domain.use_case

import com.example.test.data.repository.NoteRepository
import com.example.test.domain.model.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    operator fun invoke(): Flow<List<Note>> {
        return noteRepository.getAllNotes()
    }

}