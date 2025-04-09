package dev.haas.learn

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import dev.haas.learn.ui.theme.LearnTheme
import dev.haas.learn.websockets.startWebSocketServer

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    Button(onClick = { startWebSocketServer() } ) {
                       Text(text = "Start WebSerer")
                   }
                }
            }
        }
    }
}