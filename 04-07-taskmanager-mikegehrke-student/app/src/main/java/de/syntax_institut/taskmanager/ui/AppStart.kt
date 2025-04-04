@file:OptIn(ExperimentalMaterial3Api::class)

package de.syntax_institut.taskmanager.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CompareArrows
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import de.syntax_institut.taskmanager.ui.screen.FlowScreen
import de.syntax_institut.taskmanager.ui.screen.NoteDetailsScreen
import de.syntax_institut.taskmanager.ui.screen.NoteScreen
import de.syntax_institut.taskmanager.ui.screen.SettingsScreen
import kotlinx.serialization.Serializable


// ------------------ Alle Routen ------------------

@Serializable object NoteRoute
@Serializable object SettingsRoute
@Serializable object FlowRoute
@Serializable data class NoteDetailsRoute(val noteId: Long)

// ------------------ AppStart ------------------

@Composable
fun AppStart(
    modifier: Modifier = Modifier
) {
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute == NoteRoute::class.simpleName,
                    onClick = { navController.navigate(NoteRoute) },
                    icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Notizen") },
                    label = { Text("Notizen") }
                )
                NavigationBarItem(
                    selected = currentRoute == SettingsRoute::class.simpleName,
                    onClick = { navController.navigate(SettingsRoute) },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Einstellungen") },
                    label = { Text("Einstellungen") }
                )
                NavigationBarItem(
                    selected = currentRoute == FlowRoute::class.simpleName,
                    onClick = { navController.navigate(FlowRoute) },
                    icon = { Icon(Icons.AutoMirrored.Filled.CompareArrows, contentDescription = "Flow") },
                    label = { Text("Flow") }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = NoteRoute,
            modifier = modifier.padding(paddingValues)
        ) {
            composable<NoteRoute> {
                NoteScreen(
                    onNavigateToSettings = { navController.navigate(SettingsRoute) },
                    onNavigateToFlow = { navController.navigate(FlowRoute) },
                    onNavToDetails = { note ->
                        navController.navigate(NoteDetailsRoute(note.id))
                    }
                )
            }

            composable<SettingsRoute> {
                SettingsScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable<FlowRoute> {
                FlowScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable<NoteDetailsRoute> {
                NoteDetailsScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppStartPreview() {
    AppStart()
}