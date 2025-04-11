package dev.haas.learn

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.haas.learn.ipfinder.IpBroadcaster
import dev.haas.learn.ui.theme.LearnTheme
import dev.haas.learn.websockets.runHearSocket
import dev.haas.learn.websockets.startWebSocketServer

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnTheme {
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .fillMaxHeight()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
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
                        Button(onClick = { TextEditor() }) { }
                    }
                }
            }
        }
    }
}