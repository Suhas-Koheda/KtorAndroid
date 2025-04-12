package dev.haas.learn.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import dev.haas.learn.TextEditor

class TextPadScreen : Screen {
    @Composable
    override fun Content() {
        TextEditor()
    }

}