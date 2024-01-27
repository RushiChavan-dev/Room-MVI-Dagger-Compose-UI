package com.example.test.ui.view.note_list_view

import com.example.test.domain.model.Note

sealed class NoteListEvent {
    data class FilterNotes(val query: String): NoteListEvent()
    data class DeleteNote(val note: Note): NoteListEvent()
    data class EditNote(val note: Note): NoteListEvent()
    data class ViewNote(val note: Note): NoteListEvent()
    object AddNote: NoteListEvent()
    object RestoreNote: NoteListEvent()
}