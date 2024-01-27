package com.example.test.ui.view.note_list_view

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.domain.model.Note
import com.example.test.domain.use_case.NoteUseCases
import com.example.test.ui.navigation.Routes
import com.example.test.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _notes = mutableStateOf(emptyList<Note>())

    init {
        Log.d("NotesList", "starting call")
        noteUseCases.getAllNotesUseCase().onEach { result ->
            _notes.value = result
            Log.d("NotesList", "notes list:")
            Log.d("NotesList", _notes.value.toString())
        }.launchIn(viewModelScope)
    }

    var filteredNotes = _notes
    val allNotes = _notes

    private var deletedNote: Note? = null

    fun onEvent(event: NoteListEvent) {
        when(event) {
            is NoteListEvent.DeleteNote -> {

                viewModelScope.launch {
                    deletedNote = event.note
                    noteUseCases.deleteNoteUseCase(event.note)

                    try {
                        sendEvent(
                            UiEvent.ShowSnackBar(
                                "Note '${deletedNote?.title}' deleted",
                                "Undo"
                            )
                        )
                    }catch (e:Exception){
                        println("Checking the execrption : $e")
                    }

                }
            }
            is NoteListEvent.FilterNotes -> {
                val notesList = mutableListOf<Note>()
                _notes.value.forEach { note ->
                    if (note.title == event.query) {
                        notesList.add(note)
                    }
                }
                filteredNotes.value = notesList
            }
            is NoteListEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.insertNoteUseCase(deletedNote!!)
                }
            }
            is NoteListEvent.AddNote -> {
                sendEvent(
                    UiEvent.Navigate(Routes.AddNoteScreen.route)
                )
            }
            is NoteListEvent.EditNote -> {
                sendEvent(
                    UiEvent.Navigate(Routes.AddNoteScreen.route + "?noteId=${event.note.id}")
                )
            }
            is NoteListEvent.ViewNote -> {
                sendEvent(
                    UiEvent.Navigate(Routes.NotePreviewScreen.route + "?noteId=${event.note.id}")
                )
            }
        }
    }

    fun refreshNotesList() {
        filteredNotes = _notes
    }

    private fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}