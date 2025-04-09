package dev.haas.learn.ipfinder

import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.aSocket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.net.DatagramPacket
import java.net.InetAddress
import java.net.NetworkInterface

object IpBroadcaster {

    // Get the device's local IPv4 address
    fun getLocalIpAddress(): String {
        return NetworkInterface.getNetworkInterfaces()
            .asSequence()
            .flatMap { it.inetAddresses.asSequence() }
            .filter {
                !it.isLoopbackAddress &&
                        it.hostAddress?.contains('.') == true // IPv4 only
            }
            .find { it.isSiteLocalAddress } // Prefer private IPs
            ?.hostAddress
            ?: throw IllegalStateException("No valid IPv4 address found")
    }

    // Broadcast the IP address to the local network
    fun broadcastIp() = runBlocking {
        val selectorManager = SelectorManager(Dispatchers.IO)
        val socket = aSocket(selectorManager).udp().bind(
            localAddress = InetAddress.getByName("0.0.0.0"),
            port = 0 // Let system choose random port
        )

        try {
            socket.setBroadcast(true) // Enable broadcast

            val message = "IP_ADDRESS:${getLocalIpAddress()}"
            val broadcastPacket = DatagramPacket(
                message.toByteArray(),
                message.length,
                InetAddress.getByName("255.255.255.255"), // Broadcast address
                9002 // Destination port
            )

            socket.send(broadcastPacket)
            println("Broadcast sent: $message")
        } catch (e: Exception) {
            println("Broadcast failed: ${e.message}")
        } finally {
            socket.close()
        }
    }
}

// Usage example
fun main() {
    IpBroadcaster.broadcastIp()
}