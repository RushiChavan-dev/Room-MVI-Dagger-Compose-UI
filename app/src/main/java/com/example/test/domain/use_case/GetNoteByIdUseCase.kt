package com.example.test.domain.use_case

import com.example.test.data.repository.NoteRepository
import com.example.test.domain.model.Note
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }

}