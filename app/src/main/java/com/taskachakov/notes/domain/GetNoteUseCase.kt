package com.taskachakov.notes.domain

class GetNoteUseCase(
    private val repository: NotesRepository
)  {
    operator fun invoke(noteId: Int): Note {
        return repository.getNote(noteId)
    }
}