package com.example.test.ui.view.add_note_view

sealed class AddNoteEvent {
    object SaveNote: AddNoteEvent()
    data class OnTitleChange(val title: String): AddNoteEvent()
    data class OnDescriptionChange(val description: String): AddNoteEvent()
    data class OnColorChange(val colorValue: Long): AddNoteEvent()
}