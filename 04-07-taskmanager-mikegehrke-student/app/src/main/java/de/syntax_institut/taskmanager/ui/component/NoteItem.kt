package de.syntax_institut.taskmanager.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.syntax_institut.taskmanager.data.model.Note

@Composable
fun NoteItem(
    note: Note,
    onDeleteClick: (Note) -> Unit,
    onClick: (Note) -> Unit, // ✅ explizit required!
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(note) } // ✅ öffnet DetailsScreen
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = note.text,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconButton(
                onClick = { onDeleteClick(note) },
                modifier = Modifier.align(Alignment.Top)
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Notiz löschen")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteItemPreview() {
    NoteItem(
        note = Note(
            id = 1,
            title = "Test Notiz",
            text = "Das ist ein Vorschau-Text für die NoteItem-Karte, der gekürzt angezeigt wird."
        ),
        onDeleteClick = {},
        onClick = {}
    )
}