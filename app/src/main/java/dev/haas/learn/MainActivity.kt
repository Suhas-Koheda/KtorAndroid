package dev.haas.learn

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.navigator.Navigator
import dev.haas.learn.navigation.MainScreen
import dev.haas.learn.services.WebSocketService
import dev.haas.learn.ui.theme.LearnTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnTheme {
                WebSocketServiceHandler()
                Navigator(screen = MainScreen())
            }
        }
    }
}

@Composable
fun WebSocketServiceHandler() {
    val context = LocalContext.current
    val defaultIp = remember { "172.20.161.135" }

    LaunchedEffect(Unit) {
        try {
            startWebSocketService(context, defaultIp)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

private fun startWebSocketService(context: android.content.Context, ip: String) {
    Intent(context, WebSocketService::class.java).apply {
        putExtra("IP_ADDRESS", ip)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(this)
        } else {
            context.startService(this)
        }
    }
}