package dev.haas.learn.navigation

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor

class TextPadScreen : Screen {
    @Composable
    override fun Content() {
        val editorState = rememberRichTextState()

        Text(editorState.toText())

        RichTextEditor(
            state = editorState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .height(IntrinsicSize.Min)
        )
        editorState.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
    }

}