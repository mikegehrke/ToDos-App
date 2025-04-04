package de.syntax_institut.taskmanager.data

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String // jetzt explizit String!
)