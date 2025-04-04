package de.syntax_institut.taskmanager.ui.screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax_institut.taskmanager.ui.viewmodel.FlowViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlowScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    flowViewModel: FlowViewModel = viewModel()
) {
    val userList by flowViewModel.allUsers.collectAsState()
    val favoriteList by flowViewModel.favoriteUsers.collectAsState()
    var showFavorites by remember { mutableStateOf(false) }
    val numberString by flowViewModel.stringStateFlow.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("FlowScreen") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Zurück")
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
            Text(numberString)

            Row {
                TextButton(onClick = { flowViewModel.decrease() }) { Text("-") }
                TextButton(onClick = { flowViewModel.increase() }) { Text("+") }
            }

            Divider()

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Nur Favoriten")
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = showFavorites,
                    onCheckedChange = { showFavorites = it }
                )
            }

            Button(onClick = { flowViewModel.addRandomUser() }) {
                Text("Zufälligen Nutzer hinzufügen")
            }

            LazyColumn {
                val usersToShow = if (showFavorites) favoriteList else userList
                items(usersToShow) { user ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(user.name, modifier = Modifier.weight(1f))
                        Button(onClick = { flowViewModel.toggleFavorite(user) }) {
                            Text(if (user.isFavorite) "★ Entfernen" else "☆ Favorit")
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
private fun FlowScreenPreview(){
    FlowScreen(
        onNavigateBack = {},
        modifier = Modifier.fillMaxSize()
    )
}
