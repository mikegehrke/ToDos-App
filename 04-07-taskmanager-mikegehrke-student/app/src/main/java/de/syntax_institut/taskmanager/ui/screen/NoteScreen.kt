package de.syntax_institut.taskmanager.ui.screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax_institut.taskmanager.data.model.Note
import de.syntax_institut.taskmanager.ui.component.NoteItem
import de.syntax_institut.taskmanager.ui.viewmodel.NoteViewModel
import de.syntax_institut.taskmanager.ui.viewmodel.SettingsViewModel
import de.syntax_institut.taskmanager.ui.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    noteViewModel: NoteViewModel = viewModel(),
    settingsViewModel: SettingsViewModel = viewModel(),
    onNavigateToSettings: () -> Unit = {},
    onNavigateToFlow: () -> Unit = {},
    onNavToDetails: (Note) -> Unit
) {
    val userName by noteViewModel.userName.collectAsState()
    val noteList by noteViewModel.notes.collectAsState()
    val newNoteTitle by noteViewModel.newNoteTitle.collectAsState()
    val newNoteText by noteViewModel.newNoteText.collectAsState()
    val appStartCount by noteViewModel.appStartCount.collectAsState()
    val isNotificationOn by settingsViewModel.isNotificationOnStateFlow.collectAsState()
    val usersWithTasks by noteViewModel.usersWithTasks.collectAsState()


    LaunchedEffect(usersWithTasks) {
        usersWithTasks.forEach {
            Log.d("userAndTasks", "👤 ${it.user.name} - Notizen: ${it.notes.size}")
            it.notes.forEach { note ->
                Log.d("userAndTasks", "   • ${note.title} [${if (note.isDone) "done" else "todo"}]")
            }
        }
    }
    // Zusätzliche States und ViewModel:
    val userViewModel: UserViewModel = viewModel()
    val userList by userViewModel.allUsers.collectAsState()
    val selectedUser by userViewModel.selectedUser.collectAsState()
    var showUserSheet by remember { mutableStateOf(false) }
    var dropdownExpanded by remember { mutableStateOf(false) }
    var selectedUserName by remember { mutableStateOf("") }
    var newUserName by remember { mutableStateOf("") }



    var showDialog by remember { mutableStateOf(false) }
    var showAddNoteSheet by remember { mutableStateOf(false) }
    var editedUserName by remember { mutableStateOf(userName) }
    var showOnlyUndone by remember { mutableStateOf(false) }
    var isNewNoteDone by remember { mutableStateOf(false) }
    var showBobTodos by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        noteViewModel.incrementAppStartCount()
    }

    Box(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Column {
            Text("Hallo $userName", style = MaterialTheme.typography.titleMedium)
            Text(
                "App wurde gestartet: $appStartCount mal",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = onNavigateToSettings) { Text("Zu Einstellungen") }
                Button(onClick = onNavigateToFlow) { Text("Zu Flow") }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("Benachrichtigungen", fontWeight = FontWeight.Medium)
                    Text(if (isNotificationOn) "on" else "off")
                }
                Spacer(Modifier.weight(1f))
                Switch(
                    checked = isNotificationOn,
                    onCheckedChange = { settingsViewModel.updateNotificationOn(it) })
            }

            Row {
                Column {
                    Text("Benutzername", fontWeight = FontWeight.Medium)
                    Text(userName)
                }
                Spacer(Modifier.weight(1f))
                IconButton(onClick = {
                    editedUserName = userName
                    showDialog = true
                }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "edit user name")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ✅ Toggle für "Nur unerledigte anzeigen"
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Nur unerledigte Notizen anzeigen")
                Spacer(modifier = Modifier.width(8.dp))
                Switch(checked = showOnlyUndone, onCheckedChange = { showOnlyUndone = it })
            }

            Spacer(modifier = Modifier.height(8.dp))

            val filteredNotes = if (showOnlyUndone) {
                noteList.filter { !it.isDone }
            } else {
                noteList
            }

            // ✅ Liste aller Notizen
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {
                items(filteredNotes) { note ->
                    NoteItem(
                        note = note,
                        onDeleteClick = { noteViewModel.deleteNote(note) },
                        onClick = { onNavToDetails(note) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ✅ Toggle für BOBs Notizen
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("ToDos von Bob anzeigen", fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { showBobTodos = !showBobTodos }) {
                    Icon(
                        imageVector = if (showBobTodos) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Bob-Todos ein-/ausblenden"
                    )
                }
            }

// ✅ Robuster: Alle Bob-Todos zusammenführen (auch bei mehreren "Bob"s)
            val bobTasks = usersWithTasks
                .filter { it.user.name.trim().equals("Bob", ignoreCase = true) }
                .flatMap { it.notes }

            AnimatedVisibility(visible = showBobTodos && bobTasks.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("📝 Bobs Notizen", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        bobTasks.forEach { note ->
                            TextButton(onClick = { onNavToDetails(note) }) {
                                Text("• ${note.title} [${if (note.isDone) "done" else "todo"}]")
                            }
                        }
                    }
                }
            }
        }
        // ✅ FAB zum Hinzufügen neuer Notizen
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(end = 16.dp, bottom = 16.dp)
            ) {
                // FAB für Benutzerverwaltung
                FloatingActionButton(onClick = { showUserSheet = true }) {
                    Icon(Icons.Default.PersonAdd, contentDescription = "Benutzer verwalten")
                }

                // FAB für neue Notiz
                FloatingActionButton(onClick = { showAddNoteSheet = true }) {
                    Icon(Icons.Default.Add, contentDescription = "Notiz hinzufügen")
                }
            }
        }
    }
    // ✅ BottomSheet für Benutzerverwaltung
    if (showUserSheet) {
        ModalBottomSheet(onDismissRequest = { showUserSheet = false }) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Benutzer verwalten", style = MaterialTheme.typography.headlineMedium)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = newUserName,
                        onValueChange = { newUserName = it },
                        label = { Text("Name") },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = {
                        if (newUserName.isNotBlank()) {
                            userViewModel.addUser(newUserName)
                            newUserName = ""
                        }
                    }) {
                        Icon(Icons.Default.PersonAdd, contentDescription = "Benutzer hinzufügen")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                userList.forEach { user ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(user.name)
                        IconButton(onClick = { userViewModel.deleteUser(user) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Benutzer löschen")
                        }
                    }
                }
            }
        }
    }

    // ✅ Neue Notiz BottomSheet
    if (showAddNoteSheet) {
        ModalBottomSheet(onDismissRequest = { showAddNoteSheet = false }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Neue Notiz", style = MaterialTheme.typography.headlineMedium)

                TextField(
                    value = newNoteTitle,
                    onValueChange = { noteViewModel.onChangeNewNoteTitle(it) },
                    label = { Text("Titel") }
                )

                TextField(
                    value = newNoteText,
                    onValueChange = { noteViewModel.onChangeNewNoteText(it) },
                    label = { Text("Text") }
                )

                // ⬇️ ⬇️ ⬇️ HIER KOMMT DER NEUE DROPDOWN FÜR AUTOR
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Autor: ", style = MaterialTheme.typography.bodyLarge)
                    Box {
                        TextButton(onClick = { dropdownExpanded = true }) {
                            Text(selectedUser?.name ?: "Kein Benutzer")
                        }
                        DropdownMenu(
                            expanded = dropdownExpanded,
                            onDismissRequest = { dropdownExpanded = false }
                        ) {
                            userList.forEach { user ->
                                DropdownMenuItem(
                                    text = { Text(user.name) },
                                    onClick = {
                                        userViewModel.selectUser(user)
                                        dropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                // ⬇️ Dann kommt wie gehabt:
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Bereits erledigt?")
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(
                        checked = isNewNoteDone,
                        onCheckedChange = { isNewNoteDone = it }
                    )
                }

                Button(
                    onClick = {
                        noteViewModel.insertNote(
                            done = isNewNoteDone,
                            userId = selectedUser?.id ?: 0L
                        )
                        showAddNoteSheet = false
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Speichern")
                }
            }
        }
    }

    // ✅ Dialog für Benutzernamen
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Benutzernamen ändern") },
            text = {
                TextField(
                    value = editedUserName,
                    onValueChange = { editedUserName = it },
                    label = { Text("Neuer Name") },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    noteViewModel.updateUserName(editedUserName)
                    showDialog = false
                }) {
                    Text("Speichern")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Abbrechen")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NoteScreenPreview() {
    NoteScreen(onNavToDetails = {})
}