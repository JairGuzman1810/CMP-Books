package org.app.books.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import org.app.books.book.presentation.SelectedBookViewModel
import org.app.books.book.presentation.book_list.BookListScreenRoot
import org.app.books.book.presentation.book_list.BookListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

/**
 * [App] is the root composable function for the application.
 *
 * This function sets up the main UI of the app, which is the navigation graph.
 * It uses [NavHost] to manage navigation between different screens.
 * It also provides a preview for the UI in the IDE.
 *
 * Since this is a Compose Multiplatform app, this composable can be used across
 * different platforms (Android, iOS, Desktop, Web).
 */
@Composable
@Preview
fun App() {
    MaterialTheme {
        // Creates a NavController to manage navigation within the app.
        val navController = rememberNavController()

        // Defines the navigation graph for the app.
        NavHost(
            navController = navController,
            startDestination = Route.BookGraph
        ) {
            // Defines the navigation graph for book-related screens.
            navigation<Route.BookGraph>(
                startDestination = Route.BookList
            ) {
                // Defines the route for the BookList screen.
                composable<Route.BookList> {
                    // Retrieves an instance of BookListViewModel using Koin.
                    val viewModel: BookListViewModel = koinViewModel()

                    // Retrieves a shared instance of SelectedBookViewModel using Koin.
                    val selectedBookViewModel =
                        it.sharedKoinViewModel<SelectedBookViewModel>(navController)

                    // Resets the selected book to null when the BookList screen is launched.
                    LaunchedEffect(true) {
                        selectedBookViewModel.onSelectedBook(null)
                    }

                    // Display the root of the book list screen.
                    BookListScreenRoot(
                        // Provides the BookListViewModel instance to the BookListScreenRoot.
                        viewModel = viewModel,
                        // Handles the book click action.
                        onBookClick = { book ->
                            // Updates the selected book in the SelectedBookViewModel.
                            selectedBookViewModel.onSelectedBook(book)
                            // Navigates to the BookDetail screen with the selected book's ID.
                            navController.navigate(Route.BookDetail(book.id))
                        }
                    )
                }

                // Defines the route for the BookDetail screen.
                composable<Route.BookDetail> {
                    // Retrieves a shared instance of SelectedBookViewModel using Koin.
                    val selectedBookViewModel =
                        it.sharedKoinViewModel<SelectedBookViewModel>(navController)

                    // Collects the selected book from the SelectedBookViewModel as a state.
                    val selectedBook by selectedBookViewModel.selectedBook.collectAsStateWithLifecycle()

                    // Displays the selected book's details.
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        // Displays the selected book's data as text.
                        Text(text = "$selectedBook")
                    }
                }
            }
        }
    }
}

/**
 * Retrieves a shared [ViewModel] instance from Koin within a navigation graph.
 *
 * This function allows sharing a [ViewModel] instance between different composable
 * within the same navigation graph.
 *
 * @param navController The [NavController] used for navigation.
 * @return A shared [ViewModel] instance of type [T].
 */
@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    // Gets the route of the parent navigation graph.
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    // Gets the back stack entry for the parent navigation graph.
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    // Returns a ViewModel instance from Koin using the parent's ViewModelStoreOwner.
    return koinViewModel(
        viewModelStoreOwner = parentEntry,
    )
}