package org.app.books

import androidx.compose.runtime.*
import io.ktor.client.engine.HttpClientEngine
import org.app.books.book.data.network.KtorRemoteBookDataSource
import org.app.books.book.data.repository.BookRepositoryImpl
import org.app.books.book.presentation.book_list.BookListScreenRoot
import org.app.books.book.presentation.book_list.BookListViewModel
import org.app.books.core.data.HttpClientFactory
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * The root composable function for the application.
 *
 * This function sets up the main UI of the app, which is the [BookListScreenRoot].
 * It also provides a preview for the UI in the IDE.
 *
 * Since this is a Compose Multiplatform app, this composable can be used across
 * different platforms (Android, iOS, Desktop, Web).
 */
@Composable
@Preview
fun App(engine: HttpClientEngine) {
    // Display the root of the book list screen.
    BookListScreenRoot(
        // Create and remember an instance of the BookListViewModel.
        viewModel = remember {
            BookListViewModel(
                bookRepository = BookRepositoryImpl(
                    remoteDataSource = KtorRemoteBookDataSource(
                        httpClient = HttpClientFactory.create(
                            engine = engine
                        )
                    )
                )
            )
        },
        // Handle the book click action.
        onBookClick = {
            // TODO: Navigate to the book details screen.
            println("Book clicked: ${it.title}")
        }
    )
}