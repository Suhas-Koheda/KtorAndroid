package dev.haas.learn.ipfinder

import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.Datagram
import io.ktor.network.sockets.InetSocketAddress
import io.ktor.network.sockets.aSocket
import io.ktor.utils.io.core.BytePacketBuilder
import io.ktor.utils.io.core.build
import io.ktor.utils.io.core.writeText
import io.ktor.utils.io.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.NetworkInterface

object IpBroadcaster {

    fun getLocalIpAddress(): String {
        return try {
            NetworkInterface.getNetworkInterfaces()
                .asSequence()
                .filter { it.name == "wlan0" || it.name.startsWith("eth") || it.name.startsWith("ap") || it.name == "wlp0s20f3" }
                .flatMap { it.inetAddresses.asSequence() }
                .filter { !it.isLoopbackAddress && it.hostAddress?.contains('.') == true }
                .find { it.isSiteLocalAddress }
                ?.hostAddress
                ?: "127.0.0.1"
        } catch (ex: Exception) {
            "127.0.0.1"
        }
    }

    fun getBroadcastAddress(): String {
        val ip = getLocalIpAddress()
        val parts = ip.split('.')
        return if (parts.size == 4) "${parts[0]}.${parts[1]}.${parts[2]}.255" else "255.255.255.255"
    }
}

fun startUdpServer() {
    GlobalScope.launch(Dispatchers.IO) {
        try {
            val selectorManager = SelectorManager(Dispatchers.IO)
            val serverSocket =
                aSocket(selectorManager).udp().bind(InetSocketAddress("0.0.0.0", 4545))
            println("Server is listening on port 4545")

            while (true) {
                val datagram = serverSocket.receive()
                val text = datagram.packet.readText()
                println("Received: $text from ${datagram.address}")
            }
        } catch (e: Exception) {
            println("Server error: ${e.message}")
        }
    }
}

fun sendUdpBroadcast() {
    CoroutineScope(Dispatchers.IO).launch {
        val selectorManager = SelectorManager(Dispatchers.IO)
        try {
            // Create a socket with broadcast enabled
            val socket = aSocket(selectorManager).udp().bind {
                broadcast = true // This is crucial for broadcast
            }

            val broadcastAddress = "192.168.130.255" // Your specific subnet broadcast
            val message = "Hello from Android! My IP is ${IpBroadcaster.getLocalIpAddress()}"

            // Build the packet
            val packet = BytePacketBuilder().apply {
                writeText(message)
            }.build()

            // Send to broadcast address
            socket.send(Datagram(packet, InetSocketAddress(broadcastAddress, 4545)))
            println("Broadcast sent to $broadcastAddress: $message")

        } catch (e: Exception) {
            println("Error sending broadcast: ${e.message}")
        } finally {
            selectorManager.close()
        }
    }
}

fun main() {
    startUdpServer()
    println("Into the server")
    readln()
}