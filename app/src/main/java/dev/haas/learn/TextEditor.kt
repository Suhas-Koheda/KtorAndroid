package dev.haas.learn

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor

@Composable
fun TextEditor() {
    val editorState = rememberRichTextState()

    Text(editorState.toText())

    RichTextEditor(
        state = editorState,
        modifier = Modifier.fillMaxSize()
    )
    editorState.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
}