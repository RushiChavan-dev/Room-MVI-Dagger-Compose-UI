package com.example.test.util

import com.example.test.domain.model.Note

sealed class UiEvent {
    object PopBackStack: UiEvent()
    data class Navigate(val route: String, val note: Note? = null): UiEvent()
    data class ShowSnackBar(
        val message: String,
        val action: String? = null
    ): UiEvent()
}
