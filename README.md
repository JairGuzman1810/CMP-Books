# Bookpedia - A Cross-Platform Book Exploration App

Bookpedia is a modern and intuitive application that allows users to discover and explore books from the Open Library. It's built using Compose Multiplatform, enabling it to run seamlessly on Android, iOS, and Desktop.

## Key Features

*   **Book Search:** Search for books using keywords and explore a vast collection of public domain books.
*   **Detailed Book Information:** View comprehensive details about each book, including title, author, cover image, description, languages, publication year, ratings, page count, and editions.
*   **Favorites:** Mark books as favorites and access them offline for later reading.
*   **Favorites List:** View all your favorite books in a dedicated list, easily accessible from the main screen.
*   **Tab Navigation:** Switch between the search results and your favorites list using intuitive tabs.
*   **Cross-Platform Compatibility:** Enjoy a consistent experience across Android, iOS, and Desktop.

## Technologies and Patterns

This project leverages a modern and robust technology stack:

*   **Compose Multiplatform:** Enables building a shared UI across multiple platforms with a single codebase.
*   **Kotlin:** The primary programming language, ensuring concise and expressive code.
*   **Clean Architecture:** A well-defined architecture that promotes separation of concerns, testability, and maintainability.
*   **MVI (Model-View-Intent):** A unidirectional data flow architecture for predictable state management.
*   **Kotlin Serialization:** Used for data serialization and deserialization.
*   **Koin:** A lightweight dependency injection framework for simplifying dependency management.
*   **Ktor:** A multiplatform HTTP client for making network requests to the Open Library API.
*   **Coil:** An image loading library for efficiently handling book cover images.
*   **KSP (Kotlin Symbol Processing):** A tool for building lightweight compiler plugins.
*   **Room:** A persistence library for storing favorite books locally.
*   **Coroutines:** Kotlin Coroutines are used for asynchronous programming, ensuring a smooth and responsive user experience.

## Dependencies

### Libraries
```
androidx-activity-compose = { module = "androidx.activity:activity-compose", version.ref = "androidx-activityCompose" }
androidx-lifecycle-viewmodel = { group = "org.jetbrains.androidx.lifecycle", name = "lifecycle-viewmodel", version.ref = "androidx-lifecycle" }
androidx-lifecycle-runtime-compose = { group = "org.jetbrains.androidx.lifecycle", name = "lifecycle-runtime-compose", version.ref = "androidx-lifecycle" }
kotlinx-coroutines-swing = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-swing", version.ref = "kotlinx-coroutines" }

jetbrains-compose-navigation = { group = "org.jetbrains.androidx.navigation", name = "navigation-compose", version.ref= "navigationCompose"}
kotlinx-serialization-json = { group = "org.jetbrains.kotlinx", name = "kotlinx-serialization-json", version.ref = "kotlinSerialization" }

koin-android = { module = "io.insert-koin:koin-android", version.ref = "koin" }
koin-androidx-compose = { module = "io.insert-koin:koin-androidx-compose", version.ref = "koin" }
koin-core = { module = "io.insert-koin:koin-core", version.ref = "koin" }
koin-compose = { module = "io.insert-koin:koin-compose", version.ref = "koin" }
koin-compose-viewmodel = { module = "io.insert-koin:koin-compose-viewmodel", version.ref = "koin" }

ktor-client-core = { module = "io.ktor:ktor-client-core", version.ref = "ktor" }
ktor-client-okhttp = { module = "io.ktor:ktor-client-okhttp", version.ref = "ktor" }
ktor-client-darwin = { module = "io.ktor:ktor-client-darwin", version.ref = "ktor" }
ktor-client-content-negotiation = { module = "io.ktor:ktor-client-content-negotiation", version.ref = "ktor" }
ktor-client-logging = { module = "io.ktor:ktor-client-logging", version.ref = "ktor" }
ktor-serialization-kotlinx-json = { module = "io.ktor:ktor-serialization-kotlinx-json", version.ref = "ktor" }
ktor-client-auth = { module = "io.ktor:ktor-client-auth", version.ref = "ktor" }

coil-compose = { module = "io.coil-kt.coil3:coil-compose", version.ref = "coil3" }
coil-compose-core = { module = "io.coil-kt.coil3:coil-compose-core", version.ref = "coil3" }
coil-network-ktor2 = { module = "io.coil-kt.coil3:coil-network-ktor2", version.ref = "coil3" }
coil-network-ktor3 = { module = "io.coil-kt.coil3:coil-network-ktor3", version.ref = "coil3" }
coil-mp = { module = "io.coil-kt.coil3:coil", version.ref = "coil3" }

androidx-room-compiler = { group = "androidx.room", name = "room-compiler", version.ref = "room" }
androidx-room-runtime = { group = "androidx.room", name = "room-runtime", version.ref = "room" }
sqlite-bundled = { module = "androidx.sqlite:sqlite-bundled", version.ref = "sqlite" }
```

### Plugins
```
androidApplication = { id = "com.android.application", version.ref = "agp" }
androidLibrary = { id = "com.android.library", version.ref = "agp" }
composeMultiplatform = { id = "org.jetbrains.compose", version.ref = "compose-multiplatform" }
composeCompiler = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
kotlinMultiplatform = { id = "org.jetbrains.kotlin.multiplatform", version.ref = "kotlin" }
jetbrains-kotlin-serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version.ref = "kotlin" }
ksp = { id = "com.google.devtools.ksp", version.ref = "ksp" }
room = { id = "androidx.room", version.ref = "room" }
```

## Installation and Setup

This section provides instructions on how to clone, build, and run the Bookpedia app on different platforms.

### Android

1.  **Clone the Repository:**

    -   Open your terminal or command prompt.
    -   Navigate to the directory where you want to store the project.
    -   Use the following command to clone the repository:
```
https://github.com/JairGuzman1810/CMP-Books/
```
2.  **Open in Android Studio:**

    -   Launch Android Studio.
    -   Select "Open an Existing Project."
    -   Navigate to the `androidMain` directory within the cloned repository and select it.
    -   Click "Open."

3.  **Sync Project with Gradle Files:**

    -   Android Studio will prompt you to sync the project with the Gradle files.
    -   Click "Sync Now" in the notification bar.

4.  **Build and Run:**

    -   Connect an Android device or start an emulator.
    -   Click the "Run" button (green play icon) to build and run the app.


### iOS

1.  **Clone the Repository:**

    -   Follow the same steps as for Android to clone the repository.

2.  **Open in Xcode:**

    -   Navigate to the `iosApp` directory within the cloned repository.
    -   Open the `iosApp.xcodeproj` file in Xcode.

3.  **Build and Run:**

    -   Select a simulator or connect an iOS device.
    -   Click the "Play" button to build and run the app.


### Desktop

1.  **Clone the Repository:**

    -   Follow the same steps as for Android to clone the repository.

2.  **Open in IntelliJ IDEA:**

    -   Open the `desktopMain` directory within the cloned repository in IntelliJ IDEA.

3.  **Build and Run:**

    -   Run the `main` function in the `desktopMain` module to build and run the desktop application.

## Screenshots

### Android

<div style="display:flex; flex-wrap:wrap; justify-content:space-between;">
    <img src="https://github.com/JairGuzman1810/CMP-Books/blob/master/resources/App-1.jpg" alt="Android Screenshot 1" width="180"/>
    <img src="https://github.com/JairGuzman1810/CMP-Books/blob/master/resources/App-2.jpg" alt="Android Screenshot 2" width="180"/>
    <img src="https://github.com/JairGuzman1810/CMP-Books/blob/master/resources/App-3.jpg" alt="Android Screenshot 3" width="180"/>
</div>

### Desktop

<div style="display:flex; flex-wrap:wrap; justify-content:space-between;">
    <img src="https://github.com/JairGuzman1810/CMP-Books/blob/master/resources/Desktop-1.PNG" alt="Desktop Screenshot 1" width="280"/>
    <img src="https://github.com/JairGuzman1810/CMP-Books/blob/master/resources/Desktop-2.PNG" alt="Desktop Screenshot 2" width="280"/>
    <img src="https://github.com/JairGuzman1810/CMP-Books/blob/master/resources/Desktop-3.PNG" alt="Desktop Screenshot 3" width="280"/>
</div>

