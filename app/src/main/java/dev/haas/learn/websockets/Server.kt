package dev.haas.learn.websockets

import io.ktor.client.HttpClient
import io.ktor.client.engine.java.Java
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.http.HttpMethod
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.request.uri
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import io.ktor.server.websocket.timeout
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.seconds

fun startWebSocketServer(){
    embeddedServer(Netty,port=1818){
        install(WebSockets){
            pingPeriod=15.seconds
            timeout=15.seconds
            maxFrameSize=10000
        }
        routing {
            webSocket("/hear") {
                send(Frame.Text("👋 Hello from Android Server!"))
                for (frame in incoming) {
                    outgoing.send(frame)
                }
            }
            get("/") {
                val uri = call.request.uri
                call.respondText { uri }
            }
        }
    }.start(wait = true)
}

suspend fun hearSocket(ip: String) {
    println("into function")
    HttpClient(Java) {
        install(io.ktor.client.plugins.websocket.WebSockets) { // Install WebSockets plugin
            maxFrameSize = 10000
        }
    }.use { client ->
        println("into client")
        client.webSocket(
            method = HttpMethod.Get,
            host = ip,
            port = 1818,
            path = "/hear"
        ) {
            println("into socket")
            send(Frame.Text("Hello from client!")) // Send initial message
            for (frame in incoming) {
                when (frame) {
                    is Frame.Text -> println("Received: ${frame.readText()}")
                    is Frame.Close -> println("Connection closed")
                    else -> println("Received frame: $frame")
                }
            }
        }
    }
}

fun runHearSocket(ip: String) = runBlocking {
    println("here")
    hearSocket(ip)
}

fun main() {
    //startWebSocketServer()
    runHearSocket("172.20.161.135")
}