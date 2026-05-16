package com.example.zennotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.zennotes.data.NoteDatabase
import com.example.zennotes.data.NoteRepository
import com.example.zennotes.ui.screens.NoteEditScreen
import com.example.zennotes.ui.screens.NoteListScreen
import com.example.zennotes.ui.theme.ZenNotesTheme
import com.example.zennotes.ui.viewmodel.NoteViewModel
import com.example.zennotes.ui.viewmodel.NoteViewModelFactory

class MainActivity : ComponentActivity() {
    private val database by lazy { NoteDatabase.getDatabase(this) }
    private val repository by lazy { NoteRepository(database.noteDao()) }
    private val viewModel: NoteViewModel by viewModels {
        NoteViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZenNotesTheme {
                ZenNotesApp(viewModel)
            }
        }
    }
}

@Composable
fun ZenNotesApp(viewModel: NoteViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "note_list") {
        composable("note_list") {
            NoteListScreen(
                viewModel = viewModel,
                onNoteClick = { note ->
                    navController.navigate("note_edit/${note.id}")
                },
                onAddNoteClick = {
                    navController.navigate("note_edit/-1")
                }
            )
        }
        composable(
            route = "note_edit/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId")
            NoteEditScreen(
                viewModel = viewModel,
                noteId = noteId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
