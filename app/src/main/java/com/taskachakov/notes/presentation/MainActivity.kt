package com.taskachakov.notes.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.taskachakov.notes.presentation.screens.creation.CreateNoteScreen
import com.taskachakov.notes.presentation.screens.editing.EditNoteScreen
import com.taskachakov.notes.presentation.screens.notes.NotesScreen
import com.taskachakov.notes.presentation.ui.theme.NotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesTheme {

                EditNoteScreen(
                    noteId = 5,
                    onFinished = {
                        Log.d("CreateNoteScreen", "Finished")
                    }
                )
//                CreateNoteScreen(
//                    onFinished = {
//                        Log.d("CreateNoteScreen", "Finished")
//                    }
//                )

//                NotesScreen(
//                    onNoteClick = {
//                        Log.d("MainActivity", "onNoteClick $it")
//                    },
//                    onAddNoteClick = {
//                        Log.d("MainActivity", "clickOnFloatingButton")
//                    }
//                )
            }
        }
    }
}
