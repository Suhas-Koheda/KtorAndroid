package dev.haas.learn.ipfinder

import android.net.nsd.NsdServiceInfo
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
            .filter { it.name == "wlp0s20f3" || it.name == "wlan0" }
            .flatMap { it.inetAddresses.asSequence() }
            .filter {
                !it.isLoopbackAddress &&
                        it.hostAddress?.contains('.') == true
            }
            .find { it.isSiteLocalAddress } // Prefer private IPs
            ?.hostAddress
            ?: throw IllegalStateException("No valid IPv4 address found")
    }

    // Broadcast the IP address to the local network
    fun broadcastIp() = runBlocking {
        val selectorManager = SelectorManager(Dispatchers.IO)
        aSocket(selectorManager).udp()

        try {
            // socket.setBroadcast(true) // Enable broadcast

            val message = "IP_ADDRESS:${getLocalIpAddress()}"
            DatagramPacket(
                message.toByteArray(),
                message.length,
                InetAddress.getByName("255.255.255.255"), // Broadcast address
                9002 // Destination port
            )

            //socket.send(broadcastPacket)
            println("Broadcast sent: $message")
        } catch (e: Exception) {
            println("Broadcast failed: ${e.message}")
        } finally {
            //  socket.close()
        }
    }
}

// Usage example
fun main() {
    println(IpBroadcaster.getLocalIpAddress())
}

object nsdimplement {
    fun service() = NsdServiceInfo().apply {
        serviceName = "Ipbroadcast"
        serviceType = "_ipbroadcst.tcp"
        port = 1820
    }

}