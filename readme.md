# Learn Android Application

This Android application demonstrates WebSocket communication between devices, IP broadcasting
functionality, and a rich text editor interface.

## Features

- **WebSocket Server & Client**: Built using Ktor for real-time communication
- **IP Broadcasting**: Discovers and shares local network IP addresses
- **Rich Text Editor**: Powered by `richeditor-compose` library
- **Navigation**: Uses Voyager for screen navigation
- **Foreground Service**: Maintains WebSocket connections even when app is closed

## Technical Stack

- **Kotlin**: Primary programming language
- **Jetpack Compose**: Modern UI toolkit
- **Ktor**: WebSocket server and client implementation
- **Voyager**: Navigation library
- **Android Foreground Services**: For persistent WebSocket connections

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/dev/haas/learn/
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
├── build.gradle.kts - Module build configuration
└── proguard-rules.pro
```

## Dependencies

Key dependencies include:

- AndroidX Core, Compose, Material3
- Ktor (server, client, websockets)
- Voyager Navigator
- RichEditor for Compose

## Setup Instructions

1. Clone the repository
2. Open in Android Studio (latest stable version recommended)
3. Sync Gradle dependencies
4. Run on device or emulator

## Configuration

The app requires these permissions:

```xml

<uses-permission android:name="android.permission.INTERNET" /><uses-permission
android:name="android.permission.ACCESS_NETWORK_STATE" /><uses-permission
android:name="android.permission.ACCESS_WIFI_STATE" /><uses-permission
android:name="android.permission.FOREGROUND_SERVICE" />
```

## Known Issues

- WebSocket service may be affected by Android battery optimization
- IP broadcasting currently filters for specific network interfaces ("wlp0s20f3" or "wlan0")

## Future Improvements

- Add WebSocket message persistence
- Implement proper error handling for network operations
- Add UI controls for service management
- Support dynamic IP configuration

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

---