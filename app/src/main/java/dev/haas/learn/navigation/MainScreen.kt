package dev.haas.learn.navigation

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.haas.learn.ipfinder.IpBroadcaster
import dev.haas.learn.websockets.runHearSocket
import dev.haas.learn.websockets.startWebSocketServer

class MainScreen : Screen {

    @Composable
    override fun Content() {
        TextField(value = "Enter the desktop ip address", onValueChange = {
            if (it.length == 15) runHearSocket(it)
        })
        Button(onClick = {
            Thread {
                startWebSocketServer()
            }.start()
        }) {
            Text("Start WebServer")
        }
        Text(text = IpBroadcaster.getLocalIpAddress())
        val navigator = LocalNavigator.currentOrThrow
        Button(onClick = { navigator.push(TextPadScreen()) }) {
            Text("Open Text Editor")
        }
    }

}