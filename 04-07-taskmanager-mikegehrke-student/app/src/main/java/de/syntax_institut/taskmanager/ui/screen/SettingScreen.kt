package de.syntax_institut.taskmanager.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax_institut.taskmanager.ui.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = viewModel()
) {
    val userName by settingsViewModel.userName.collectAsState()
    val isNotificationOn by settingsViewModel.isNotificationOnStateFlow.collectAsState()
    val darkMode by settingsViewModel.darkMode.collectAsState()
    val dailyReminder by settingsViewModel.dailyReminder.collectAsState()
    val favoriteFirst by settingsViewModel.favoriteFirst.collectAsState()
    val sortNotesBy by settingsViewModel.sortNotesBy.collectAsState()

    var showNameDialog by remember { mutableStateOf(false) }
    val isDarkMode by settingsViewModel.isDarkModeState.collectAsState()

    SectionHeader("Dark Mode")
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Dark Mode aktiv")
        Spacer(Modifier.weight(1f))
        Switch(
            checked = isDarkMode,
            onCheckedChange = { settingsViewModel.setDarkMode(it) }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Einstellungen") },
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
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            SectionHeader("Benutzername")
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(userName, modifier = Modifier.weight(1f))
                IconButton(onClick = { showNameDialog = true }) {
                    Icon(Icons.Default.Edit, contentDescription = "Name ändern")
                }
            }

            SectionHeader("Benachrichtigungen")
            SwitchRow("Aktiviert", isNotificationOn) {
                settingsViewModel.updateNotificationOn(it)
            }

            SectionHeader("Design")
            SwitchRow("Dark Mode", darkMode) {
                settingsViewModel.toggleDarkMode(it)
            }

            SectionHeader("Produktivität")
            SwitchRow("Tägliche Erinnerung", dailyReminder) {
                settingsViewModel.toggleDailyReminder(it)
            }
            SwitchRow("Favoriten zuerst anzeigen", favoriteFirst) {
                settingsViewModel.toggleFavoriteFirst(it)
            }

            SectionHeader("Sortierung")
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Sortieren nach: $sortNotesBy")
                Spacer(Modifier.weight(1f))
                Button(onClick = { settingsViewModel.setSortNotesBy("date") }) {
                    Text("Datum")
                }
                Button(onClick = { settingsViewModel.setSortNotesBy("title") }) {
                    Text("Titel")
                }
            }
        }
    }

    if (showNameDialog) {
        EditNameDialog(
            initUserName = userName,
            onSave = {
                settingsViewModel.updateName(it)
                showNameDialog = false
            },
            onDismiss = { showNameDialog = false }
        )
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

@Composable
fun SwitchRow(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Text(label)
        Spacer(modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNameDialog(
    initUserName: String,
    onSave: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var newName by remember { mutableStateOf(initUserName) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Benutzernamen ändern") },
        text = {
            TextField(
                value = newName,
                onValueChange = { newName = it },
                label = { Text("Name") }
            )
        },
        confirmButton = {
            TextButton(onClick = { onSave(newName) }) {
                Text("Speichern")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Abbrechen")
            }
        }
    )
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(onNavigateBack = {})
}
