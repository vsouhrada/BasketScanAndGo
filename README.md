# Basket Scan and Go

[![Kotlin](https://img.shields.io/badge/Kotlin-2.3.10-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-1.10.3-blue.svg?style=flat)](https://www.jetbrains.com/lp/compose-multiplatform/)
[![AGP](https://img.shields.io/badge/AGP-9.0.0-green.svg?style=flat)](https://developer.android.com/build)
[![Gradle](https://img.shields.io/badge/Gradle-9.1.0-blue.svg?style=flat&logo=gradle)](https://gradle.org)

![badge-android](http://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat)
![badge-desktop](http://img.shields.io/badge/platform-desktop-4D76CD.svg?style=flat)
![badge-ios](http://img.shields.io/badge/platform-ios-EAEAEA.svg?style=flat)
![badge-browser-wasm](https://img.shields.io/badge/platform-wasm-F8DB5D.svg?style=flat)

## Project Overview

**Basket Scan and Go** is a modern cross-platform shopping application that revolutionizes the in-store shopping experience. Customers can scan product barcodes using their mobile device, add items to a virtual basket, and complete checkout without waiting in traditional checkout lines.

The application is built using **Kotlin Multiplatform (KMP)** and **Compose Multiplatform**, enabling a shared codebase across Android, iOS, Desktop, and Web (WasmJS) platforms. This approach maximizes code reuse while delivering native performance and user experience on each platform.

### Key Features

- **Product Scanning**: Scan product barcodes using the device camera to instantly add items to your basket
- **Basket Management**: Create, view, modify, and delete shopping baskets with real-time price calculations
- **User Authentication**: Secure login and registration with session management
- **Shared Baskets**: Collaborate with family or friends by sharing baskets across devices
- **Cross-Platform**: Single codebase runs on Android, iOS, Desktop (JVM), and Web (WasmJS)
- **Offline Support**: Continue shopping even with intermittent connectivity
- **Backend Server**: Ktor-based REST API server for data persistence and synchronization

### Tech Stack

| Layer | Technology |
|-------|------------|
| **UI Framework** | Compose Multiplatform |
| **Language** | Kotlin 2.x |
| **Networking** | Ktor Client |
| **Serialization** | Kotlinx Serialization |
| **DI** | Koin |
| **Backend** | Ktor Server (Netty) |
| **Architecture** | Clean Architecture + MVI |

## Libraries used
- 🧩 [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform); for shared UI
- 🌐 [Ktor](https://github.com/ktorio/ktor); for networking
- 📦 [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization); for content negotiation
- 🕰️ [Kotlinx Datetime](https://github.com/Kotlin/kotlinx-datetime); for datetime
- 🧹 [ktlint](https://github.com/pinterest/ktlint); for Kotlin linting and formatting
- 🔄 [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines); for asynchronous programming
- 💉 [Koin](https://insert-koin.io/); for dependency injection

## Development Setup

### Server IP Configuration

When developing with a mobile device or switching networks, you need to update the server IP address in the client configuration. Helper scripts are provided in the `scripts/` folder to automate this:

**macOS/Linux:**
```bash
./scripts/update-server-ip.sh
```

**Windows:**
```cmd
scripts\update-server-ip.bat
```

These scripts will:
1. Auto-detect your machine's current IP address
2. Update the client configuration file (`BasketApiServerPathProviderImpl.kt`)

> **Note:** The server is configured to listen on `0.0.0.0` (all network interfaces), so it's accessible from any device on the same network.

## Run Instructions

### IntelliJ IDEA / Android Studio

The project includes pre-configured run configurations in `.idea/runConfigurations/`. After opening the project, you'll find these configurations in the Run/Debug dropdown:

| Configuration | Task | Description |
|---------------|------|-------------|
| **Android App** | `:apps:androidApp:installDebug` | Run Android app on device/emulator |
| **Desktop App** | `:apps:desktopApp:run` | Run desktop JVM application |
| **Web WASM App** | `:apps:webApp:wasmJsBrowserDevelopmentRun` | Run WasmJS app in browser |
| **Build All** | `build` | Build all modules |
| **Ktlint Check** | `ktlintCheck` | Run ktlint code style check |
| **iOS Framework** | `:apps:shared:embedAndSignAppleFrameworkForXcode` | Build iOS framework for Xcode |
| **Server** | `:basket-server:run` | Run the Ktor backend server |

### Fleet

Open Run Config action:
<img alt="run-config.png" src="artwork/run-config.png" />
or you can run it from file `.fleet/run.json`

### Gradle Commands

| Platform | Gradle Command |
|----------|----------------|
| Android | `./gradlew :apps:androidApp:installDebug` |
| Desktop | `./gradlew :apps:desktopApp:run` |
| Web (WasmJS) | `./gradlew :apps:webApp:wasmJsBrowserDevelopmentRun` |
| iOS Framework | `./gradlew :apps:shared:linkDebugFrameworkIosSimulatorArm64` |
| Server | `./gradlew :basket-server:run` |
| Build All | `./gradlew assemble` |

## Code Quality

### ktlint

This project uses [ktlint](https://github.com/pinterest/ktlint) for Kotlin code style checking and formatting. ktlint is a linter with built-in formatter that enforces the official Kotlin code style.

#### Available Gradle tasks

- `./gradlew ktlintCheck` - Run ktlint check on all modules
- `./gradlew ktlintFormat` - Run ktlint format on all modules to automatically fix style violations
- `./gradlew <module>:ktlintCheck` - Run ktlint check on a specific module
- `./gradlew <module>:ktlintFormat` - Run ktlint format on a specific module

#### IDE Integration

For IntelliJ IDEA / Android Studio, you can install the [ktlint plugin](https://plugins.jetbrains.com/plugin/15057-ktlint-unofficial-) to get real-time linting and formatting.

## Architecture

### Architectural Dependency Map

The application follows Clean Architecture principles with a clear separation of concerns:

```
┌─────────────────────────────────────────────────────────────────┐
│                        PRESENTATION LAYER                       │
│                                                                 │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  │
│  │   ViewModels    │  │  Screen States  │  │     Events      │  │
│  └────────┬────────┘  └─────────────────┘  └─────────────────┘  │
│           │                                                     │
└───────────┼─────────────────────────────────────────────────────┘
            │
            ▼
┌─────────────────────────────────────────────────────────────────┐
│                          DOMAIN LAYER                           │
│                                                                 │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  │
│  │    Use Cases    │  │ Domain Models   │  │  Repositories   │  │
│  │    Interface    │  │                 │  │   Interface     │  │
│  └────────┬────────┘  └─────────────────┘  └────────┬────────┘  │
│           │                                         │           │
└───────────┼─────────────────────────────────────────┼───────────┘
            │                                         │
            ▼                                         ▼
┌─────────────────────────────────────────────────────────────────┐
│                           DATA LAYER                            │
│                                                                 │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  │
│  │ Repository Impl │  │   Data Sources  │  │    DTOs/APIs    │  │
│  └────────┬────────┘  └────────┬────────┘  └────────┬────────┘  │
│           │                    │                    │           │
└───────────┼────────────────────┼────────────────────┼───────────┘
            │                    │                    │
            ▼                    ▼                    ▼
┌─────────────────────────────────────────────────────────────────┐
│                        PLATFORM LAYER                           │
│                                                                 │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  │
│  │     Result      │  │     KLogger     │  │      KTime      │  │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘  │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

### Module Dependencies

```
apps/
├── shared (KMP Shared Code - Compose UI)
│   ├── core/presentation
│   └── core/di
│
├── androidApp (Android Application)
│   └── apps/shared
│
├── desktopApp (Desktop JVM Application)
│   └── apps/shared
│
└── webApp (WasmJS Web Application)
    └── apps/shared

core/
├── designSystem (UI Components & Theme)
│
├── di (Dependency Injection)
│   ├── core/logic/presentation
│   ├── core/logic/data
│   └── platform/ktime
│
└── logic/
    ├── presentation (ViewModels, States)
    │   ├── core/logic/domain
    │   ├── core/logic/data
    │   ├── core/designSystem
    │   └── platform/klogger
    │
    ├── domain (Use Cases, Models)
    │   ├── platform/result
    │   └── platform/klogger
    │
    └── data (Repositories, Data Sources)
        ├── core/logic/domain
        └── platform/klogger

platform/
├── klogger (Logging utilities)
│   └── platform/ktime
├── ktime (Date and time utilities)
└── result (Result monad for error handling)
    └── platform/ktime
```

### Detailed Module Dependency Map

The following diagram shows a more detailed view of module dependencies in the project:

```
+---------------------+     +---------------------+     +---------------------+
|        APPS         |     |        CORE         |     |        LOGIC        |
|       MODULES       |     |       MODULES       |     |       MODULES       |
+---------------------+     +---------------------+     +---------------------+
| :apps:shared        |     | :designSystem       |     | :presentation       |
| (KMP Shared UI)     |     | (UI Components)     |     | (ViewModels, States)|
|                     |     |                     |     |                     |
| :apps:androidApp    |     | :di                 |     | :domain             |
| (Android App)       |     | (Dependency         |     | (Use Cases, Models) |
|                     |     |  Injection)         |     |                     |
| :apps:desktopApp    |     |                     |     | :data               |
| (Desktop JVM App)   |     |                     |     | (Repositories,      |
|                     |     |                     |     |  Data Sources)      |
| :apps:webApp        |     |                     |     |                     |
| (WasmJS Web App)    |     |                     |     |                     |
|                     |     |                     |     |                     |
| :basket-server      |     |                     |     |                     |
| (Ktor Backend)      |     |                     |     |                     |
+---------------------+     +---------------------+     +---------------------+
        |  |                       |   |                      |   |   |
        |  |                       |   |                      |   |   |
        |  +---------------------->+   |                      |   |   |
        |                          |   |                      |   |   |
        |  +---------------------->+   |                      |   |   |
        |  |                       |   +--------------------->+   |   |
        |  |                       |                          |   |   |
        |  |                       +------------------------->+   |   |
        |  |                                                  |   |   |
        +--+------------------------------------------------->+   |   |
           |                                                  v   v   v
           |                                          +---------------------+
           |                                          |      PLATFORM       |
           |                                          |       MODULES       |
           |                                          +---------------------+
           |                                          | :klogger            |
           |                                          | (Logging utilities) |
           |                                          |                     |
           |                                          | :ktime              |
           +----------------------------------------->| (Date and time      |
                                                      |  utilities)         |
                                                      |                     |
                                                      | :result             |
                                                      | (Result monad for   |
                                                      |  error handling)    |
                                                      +---------------------+

Dependencies:
- apps:androidApp -> apps:shared
- apps:desktopApp -> apps:shared
- apps:webApp -> apps:shared
- apps:shared -> presentation, di
- di -> presentation, data, ktime
- presentation -> domain, data, designSystem, klogger
- domain -> result, klogger
- data -> domain, klogger
- klogger -> ktime
- result -> ktime
```

This diagram illustrates:
1. **Apps Modules**: Platform-specific entry points (Android, Desktop, Web) depending on shared KMP code
2. **Core Modules**: Shared UI components and dependency injection
3. **Logic Modules**: Business logic implementation following Clean Architecture
4. **Platform Modules**: Utility modules used across the application

The arrows indicate dependencies between modules, showing how they rely on each other.

### Key Components

1. **Apps Modules** (`apps/`):
   - **shared**: KMP module with shared Compose UI code for all platforms
   - **androidApp**: Android application entry point
   - **desktopApp**: Desktop JVM application entry point
   - **webApp**: WasmJS web application entry point

2. **Core Modules** (`core/`):
   - **designSystem**: UI components and styling
   - **di**: Dependency injection setup with Koin
   - **logic**: Business logic implementation
     - **data**: Repositories and data sources (Ktor client)
     - **domain**: Use cases and domain models
     - **presentation**: ViewModels and UI state management

3. **Platform Modules** (`platform/`):
   - **klogger**: Logging utilities
   - **ktime**: Date and time utilities
   - **result**: Result monad for error handling

4. **Server Module** (`server/`):
   - **basket-server**: Ktor-based backend server implementation ([documentation](doc/basket-server.md))

5. **iOS App** (`iosApp/`):
   - Native iOS app using the shared framework from `apps:shared`

## Showcase

Below are screenshots of the Basket Scan and Go application running on different platforms.

### Android
<img alt="Android App Screenshot" src="artwork/android.png" width="300" />

### iOS
<img alt="iOS App Screenshot" src="artwork/ios.png" width="300" />

### Desktop
<img alt="Desktop App Screenshot" src="artwork/desktop.png" width="600" />

### Web (Js & WasmJs)
<img alt="Web App Screenshot" src="artwork/web.png" width="600" />
