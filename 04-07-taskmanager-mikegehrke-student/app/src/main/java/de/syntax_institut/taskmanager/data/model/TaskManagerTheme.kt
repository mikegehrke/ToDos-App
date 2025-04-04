package de.syntax_institut.taskmanager.data.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import de.syntax_institut.taskmanager.ui.theme.Pink40
import de.syntax_institut.taskmanager.ui.theme.Pink80
import de.syntax_institut.taskmanager.ui.theme.Purple40
import de.syntax_institut.taskmanager.ui.theme.Purple80
import de.syntax_institut.taskmanager.ui.theme.PurpleGrey40
import de.syntax_institut.taskmanager.ui.theme.PurpleGrey80
import de.syntax_institut.taskmanager.ui.theme.Typography

// Deine benutzerdefinierten Farben
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun TaskManagerTheme(
    useDarkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (useDarkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}