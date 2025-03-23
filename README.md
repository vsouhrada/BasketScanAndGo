# Basket Scan and Go

[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.10-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org)

![badge-android](http://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat)
![badge-wearos](http://img.shields.io/badge/platform-wearos-8ECDA0.svg?style=flat)
![badge-desktop](http://img.shields.io/badge/platform-desktop-4D76CD.svg?style=flat)
![badge-desktop](http://img.shields.io/badge/platform-ios-EAEAEA.svg?style=flat)
![badge-browser-js](https://img.shields.io/badge/platform-js-F8DB5D.svg?style=flat)
![badge-browser-wasm](https://img.shields.io/badge/platform-wasm-F8DB5D.svg?style=flat)

## Libraries used
- 🧩 [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform); for shared UI
- 🌐 [Ktor](https://github.com/ktorio/ktor); for networking
- 📦 [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization); for content negotiation
- 🕰️ [Kotlinx Datetime](https://github.com/Kotlin/kotlinx-datetime); for datetime
- 🧹 [ktlint](https://github.com/pinterest/ktlint); for Kotlin linting and formatting
- ... TBD

## Run instructions

### Fleet

Open Run Config action:
<img alt="run-config.png" src="artwork/run-config.png" />
or you can run it from file `.fleet/run.json`


### IntelliJ IDEA
TBD

### Gradle
| platform | gradle command                                                                                                                      |
|----------|-------------------------------------------------------------------------------------------------------------------------------------|
| android  | `TBD`                                                                                                 |
| ios      | `/Applications/Xcode.app/Contents/Developer/usr/bin/xcodebuild -project app/ios/ios.xcodeproj -scheme ComposeApp -configuration Debug` |
| desktop  | `./gradlew :composeApp:desktopRun -DmainClass=com.basket.sample.scango.DesktopAppKt`                                                                                                        |
| js       | `./gradlew :composeApp:jsBrowserDevelopmentRun`                                                                                        |
| wasm     | `./gradlew :composeApp:wasmJsBrowserDevelopmentRun`

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

## Showcase
TBD
### Android
### iOS
### Desktop
### Web (Js & WasmJs)
