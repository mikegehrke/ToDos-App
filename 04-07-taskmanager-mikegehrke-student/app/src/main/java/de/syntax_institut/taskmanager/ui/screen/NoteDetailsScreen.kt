package de.syntax_institut.taskmanager.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax_institut.taskmanager.ui.viewmodel.NoteDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailsScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    noteDetailsViewModel: NoteDetailsViewModel = viewModel()
) {
    val note by noteDetailsViewModel.noteStateFlow.collectAsState()

    // Achtung: direkt mit dem Flow-Wert binden!
    var title by remember(note.id) { mutableStateOf(note.title) }
    var text by remember(note.id) { mutableStateOf(note.text) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notiz bearbeiten") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Zurück")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Titel") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Text") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Erledigt?")
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = note.isDone,
                    onCheckedChange = {
                        noteDetailsViewModel.toggleDoneStatus()
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        noteDetailsViewModel.updateNote(title, text, note.isDone)
                        onNavigateBack()
                    }
                ) {
                    Text("Speichern")
                }

                Button(
                    onClick = {
                        noteDetailsViewModel.deleteNote()
                        onNavigateBack()
                    }
                ) {
                    Text("Löschen")
                }

                Button(
                    onClick = { onNavigateBack() }
                ) {
                    Text("Abbrechen")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteDetailsScreenPreview() {
    NoteDetailsScreen(
        onNavigateBack = {}
    )
}