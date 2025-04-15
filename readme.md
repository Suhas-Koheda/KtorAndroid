# Learn Android Application

A feature-rich Android application demonstrating WebSocket communication, IP broadcasting, and a
rich text editor interface.

## Features

- **WebSocket Server & Client**: Real-time bidirectional communication using Ktor
- **IP Broadcasting**: Discovers and shares local network IP addresses
- **Rich Text Editor**: Powered by `richeditor-compose` with formatting capabilities
- **Note Management**: CRUD operations for notes with Room database
- **Foreground Service**: Maintains WebSocket connections in background
- **Modern UI**: Built with Jetpack Compose and Material 3

## Technical Stack

- **Kotlin**: Primary programming language
- **Jetpack Compose**: Modern declarative UI toolkit
- **Ktor**: WebSocket server and client implementation
- **Voyager**: Navigation library for Compose
- **Room**: Local database for note persistence
- **Coroutines**: Asynchronous programming

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/learn-android.git
   ```

2. Open the project in Android Studio (latest stable version recommended)

3. Sync Gradle dependencies

4. Run on an Android device or emulator (minSdk 32)

## Configuration

The app requires these permissions in `AndroidManifest.xml`:

```xml

<uses-permission android:name="android.permission.INTERNET" /><uses-permission
android:name="android.permission.ACCESS_WIFI_STATE" /><uses-permission
android:name="android.permission.CHANGE_WIFI_STATE" /><uses-permission
android:name="android.permission.CHANGE_WIFI_MULTICAST_STATE" /><uses-permission
android:name="android.permission.ACCESS_NETWORK_STATE" /><uses-permission
android:name="android.permission.FOREGROUND_SERVICE" />
```

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/dev/haas/learn/
│   │   │   ├── database/ - Room database entities and DAOs
│   │   │   ├── ipfinder/ - IP discovery and broadcasting
│   │   │   ├── navigation/ - Screen navigation components
│   │   │   ├── services/ - Foreground service implementation
│   │   │   ├── websockets/ - WebSocket server and client
│   │   │   ├── ui/theme/ - Compose theming
│   │   │   └── MainActivity.kt - Entry point
│   │   ├── res/ - Resources and assets
│   │   └── AndroidManifest.xml
│   ├── test/ - Unit tests
│   └── androidTest/ - Instrumentation tests
```

## Key Components

- **WebSocketService**: Maintains persistent WebSocket connections
- **NoteDatabase**: Room database for local note storage
- **IpBroadcaster**: Discovers and broadcasts local IP addresses
- **MainScreen**: Primary navigation hub with feature access
- **TextPadScreen**: Rich text editor implementation
- **NoteScreen**: Note management interface

## Dependencies

Key dependencies include:

- AndroidX Core, Compose, Material3
- Ktor (server, client, websockets)
- Voyager Navigator
- RichEditor for Compose
- Room Database
- Kotlinx DateTime

## Usage

1. **WebSocket Communication**:
    - Start WebSocket server from MainScreen
    - Connect clients using discovered IP addresses

2. **IP Broadcasting**:
    - Tap "Send UDP Broadcast" to discover devices
    - Requires location permissions on Android 12+

3. **Rich Text Editor**:
    - Navigate to Text Pad from MainScreen
    - Format text and add links

4. **Note Management**:
    - Create, read, update, and delete notes
    - Persisted locally using Room database

## Known Issues

- WebSocket service may be affected by Android battery optimization
- IP broadcasting currently filters for specific network interfaces
- Location permissions required for network discovery on newer Android versions

## Future Improvements

- Add WebSocket message persistence
- Implement proper error handling for network operations
- Support dynamic IP configuration
- Add UI controls for service management
- Implement data synchronization between devices

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

```