package dev.haas.learn.navigation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.haas.learn.ipfinder.sendUdpBroadcast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainScreen : Screen {

    @Composable
    override fun Content() {
        val context = LocalContext.current
        LocalNavigator.currentOrThrow

        // List of required permissions
        val requiredPermissions = mutableListOf(
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_MULTICAST_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE
        ).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                add(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                add(Manifest.permission.NEARBY_WIFI_DEVICES)
            }
        }.toTypedArray()

        // Check if permissions are already granted
        fun hasPermissions(): Boolean {
            return requiredPermissions.all {
                ContextCompat.checkSelfPermission(
                    context,
                    it
                ) == PackageManager.PERMISSION_GRANTED
            }
        }

        val permissionLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions.all { it.value }) {
                CoroutineScope(Dispatchers.IO).launch {
                    sendUdpBroadcast()
                }
            } else {
                Toast.makeText(
                    context,
                    "Permissions required for network operations",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                if (hasPermissions()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        sendUdpBroadcast()
                    }
                } else {
                    permissionLauncher.launch(requiredPermissions)
                }
            }) {
                Text("Send UDP Broadcast")
            }
        }
    }
}