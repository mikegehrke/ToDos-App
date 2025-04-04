package de.syntax_institut.taskmanager.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax_institut.taskmanager.data.User
import de.syntax_institut.taskmanager.ui.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = viewModel()
) {
    val userList by userViewModel.allUsers.collectAsState()
    var newUserName by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Benutzer verwalten", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Eingabe + Button zum Hinzufügen
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = newUserName,
                onValueChange = { newUserName = it },
                label = { Text("Neuer Benutzername") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    if (newUserName.isNotBlank()) {
                        userViewModel.insertUser(newUserName)
                        newUserName = ""
                    }
                }
            ) {
                Icon(Icons.Default.PersonAdd, contentDescription = "Benutzer hinzufügen")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 🔹 Liste aller User
        Text("Vorhandene Benutzer:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(userList) { user ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(user.name, fontWeight = FontWeight.Medium)
                        IconButton(onClick = { userViewModel.deleteUser(user) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Benutzer löschen")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserScreenPreview() {
    UserScreen()
}