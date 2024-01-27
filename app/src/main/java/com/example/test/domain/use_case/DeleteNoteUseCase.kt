package com.example.test.domain.use_case

import com.example.test.data.repository.NoteRepository
import com.example.test.domain.model.Note
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }

}