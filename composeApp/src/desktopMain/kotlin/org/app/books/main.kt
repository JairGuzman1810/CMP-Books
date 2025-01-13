package org.app.books

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.app.books.di.initKoin
/**
 * The main entry point for the desktop application.
 *
 * This function initializes the Koin dependency injection framework and
 * sets up the Compose Desktop application window.
 */
fun main() {
    // Initializes Koin for dependency injection.
    initKoin()
    // Starts the Compose Desktop application.
    application {
        // Creates a window for the application.
        Window(
            // Defines the action to perform when the window is closed.
            onCloseRequest = ::exitApplication,
            // Sets the title of the window.
            title = "Books",
        ) {
            // Renders the main application UI.
            App()
        }
    }
}