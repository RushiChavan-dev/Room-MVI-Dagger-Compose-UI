package com.example.test.domain.use_case

data class NoteUseCases(
    val getAllNotesUseCase: GetAllNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val getNoteByIdUseCase: GetNoteByIdUseCase,
    val insertNoteUseCase: InsertNoteUseCase
)