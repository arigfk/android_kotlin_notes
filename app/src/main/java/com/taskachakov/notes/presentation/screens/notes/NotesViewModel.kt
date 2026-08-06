@file:OptIn(ExperimentalCoroutinesApi::class)

package com.taskachakov.notes.presentation.screens.notes

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taskachakov.notes.data.NotesRepositoryImpl
import com.taskachakov.notes.data.TestNotesRepositoryImpl
import com.taskachakov.notes.domain.AddNoteUseCase
import com.taskachakov.notes.domain.DeleteNoteUseCase
import com.taskachakov.notes.domain.EditNoteUseCase
import com.taskachakov.notes.domain.GetAllNotesUseCase
import com.taskachakov.notes.domain.GetNoteUseCase
import com.taskachakov.notes.domain.Note
import com.taskachakov.notes.domain.SearchNotesUseCase
import com.taskachakov.notes.domain.SwitchPinnedStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val searchNotesUseCase: SearchNotesUseCase,
    private val switchPinnedStatusUseCase: SwitchPinnedStatusUseCase
): ViewModel() {


    private val query = MutableStateFlow("")

    private val _state = MutableStateFlow(NotesScreenState())
    val state = _state.asStateFlow()

    init {
        query
            .onEach {input ->
                _state.update { it.copy(query = input) }
            }
            .flatMapLatest {input ->
                if (input.isBlank()) {
                    getAllNotesUseCase()
                } else {
                    searchNotesUseCase(input)
                }
            }
            .onEach {notes ->
                val pinnedNotes = notes.filter {it.isPinned}
                val otherNotes = notes.filter {!it.isPinned}
                _state.update { it.copy(pinnedNotes = pinnedNotes, otherNotes = otherNotes) }
            }
            .launchIn(viewModelScope)
    }
    fun processCommand(command: NotesCommand) {
        viewModelScope.launch {
            when (command) {
                is NotesCommand.InputSearchQuery -> {
                    query.update { command.query.trim() }
                }
                is NotesCommand.SwitchPinnedStatus -> {
                    switchPinnedStatusUseCase(command.noteId)
                }
            }
        }
    }

}

sealed interface NotesCommand {
    data class InputSearchQuery(val query: String): NotesCommand

    data class SwitchPinnedStatus(val noteId: Int): NotesCommand

}

data class NotesScreenState(
    val query: String = "",
    val pinnedNotes: List<Note> = listOf(),
    val otherNotes: List<Note> = listOf()
)
