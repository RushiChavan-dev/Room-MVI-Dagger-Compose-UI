package com.example.test.domain.use_case

import com.example.test.data.repository.NoteRepository
import com.example.test.domain.model.Note
import com.example.test.util.InvalidNoteException
import javax.inject.Inject
import kotlin.jvm.Throws

class InsertNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isEmpty()) {
            throw InvalidNoteException("Note title can not be empty")
        }
        repository.insertNote(note)
    }

}