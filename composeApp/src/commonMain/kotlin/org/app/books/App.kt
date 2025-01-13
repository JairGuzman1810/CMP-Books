package org.app.books

import androidx.compose.runtime.Composable
import org.app.books.book.presentation.book_list.BookListScreenRoot
import org.app.books.book.presentation.book_list.BookListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

/**
 * [App] is the root composable function for the application.
 *
 * This function sets up the main UI of the app, which is the [BookListScreenRoot].
 * It also provides a preview for the UI in the IDE.
 *
 * Since this is a Compose Multiplatform app, this composable can be used across
 * different platforms (Android, iOS, Desktop, Web).
 */
@Composable
@Preview
fun App() {
    // Retrieves an instance of BookListViewModel using Koin.
    val viewModel: BookListViewModel = koinViewModel()

    // Display the root of the book list screen.
    BookListScreenRoot(
        // Provides the BookListViewModel instance to the BookListScreenRoot.
        viewModel = viewModel,
        // Handles the book click action.
        onBookClick = { book ->
            // TODO: Navigate to the book details screen.
            println("Book clicked: ${book.title}")
        }
    )
}