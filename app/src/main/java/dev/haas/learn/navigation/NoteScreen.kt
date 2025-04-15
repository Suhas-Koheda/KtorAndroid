package dev.haas.learn.navigation

import Note
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import dev.haas.learn.database.NoteViewModel

class NoteTestScreen(private val viewModel: NoteViewModel) : Screen {
    var i: Long = 0

    @Composable
    override fun Content() {
        var text by remember { mutableStateOf("") }
        val notes by viewModel.allNotes.collectAsState(initial = emptyList())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Input field
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Note content") },
                modifier = Modifier.fillMaxWidth()
            )

            // Buttons row
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = {
                    if (text.isNotBlank()) {
                        viewModel.insert(
                            Note(
                                content = text,
                                id = i,
                                title = "TODO()",
                                timesEdited = 0
                            )
                        )
                        text = ""
                    }
                    i++
                }) {
                    Text("Add Note")
                }

                Button(onClick = {
                    if (notes.isNotEmpty()) {
                        viewModel.delete(notes.last())
                    }
                }) {
                    Text("Delete Last")
                }
            }

            // Notes list
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(notes) { note ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = note.content,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}