package org.app.books

import androidx.compose.ui.window.ComposeUIViewController
import org.app.books.di.initKoin

/**
 * Creates and returns a [ComposeUIViewController] for iOS.
 *
 * This function is the entry point for the shared Compose UI in the iOS application.
 * It initializes the Koin dependency injection framework and sets up the
 * Compose UI hierarchy.
 *
 * @return A [ComposeUIViewController] that hosts the shared Compose UI.
 */
fun MainViewController() = ComposeUIViewController(
    // Creates and returns a ComposeUIViewController.
    configure = {
        // Initializes Koin for dependency injection.
        initKoin()
    }
) {
    // Renders the main application UI.
    App()
}