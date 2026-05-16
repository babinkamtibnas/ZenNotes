package com.example.zennotes.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.zennotes.data.Note
import com.example.zennotes.ui.viewmodel.NoteViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(
    viewModel: NoteViewModel,
    noteId: Int?,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    LaunchedEffect(noteId) {
        if (noteId != null && noteId != -1) {
            viewModel.getNoteById(noteId)?.let { note ->
                title = note.title
                content = note.content
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (noteId == null || noteId == -1) "Add Note" else "Edit Note") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val note = if (noteId == null || noteId == -1) {
                            Note(title = title, content = content)
                        } else {
                            Note(id = noteId, title = title, content = content)
                        }
                        if (noteId == null || noteId == -1) {
                            viewModel.insert(note)
                        } else {
                            viewModel.update(note)
                        }
                        onBack()
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Save")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                    unfocusedContainerColor = androidx.compose.ui.graphics.Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = content,
                onValueChange = { content = it },
                placeholder = { Text("Content") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                    unfocusedContainerColor = androidx.compose.ui.graphics.Color.Transparent
                )
            )
        }
    }
}
