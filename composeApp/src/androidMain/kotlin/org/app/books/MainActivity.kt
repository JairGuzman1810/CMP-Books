package org.app.books

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.app.books.app.App

/**
 * [MainActivity] is the main activity for the Android application.
 *
 * This activity serves as the entry point for the app and sets up the
 * Compose UI using the [App] composable.
 */
class MainActivity : ComponentActivity() {

    /**
     * Called when the activity is first created.
     *
     * This is where we set up the Compose UI for the app using the [setContent] function.
     * The [App] composable is used as the root of the UI hierarchy.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content of the activity to the App composable.
        setContent {
            // Renders the main application UI.
            App()
        }
    }
}