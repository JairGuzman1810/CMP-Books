package org.app.books.book.presentation.book_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.app.books.book.domain.Book

/**
 * Composable function that displays a scrollable list of books.
 *
 * This component uses a [LazyColumn] to efficiently render a list of [Book] items.
 * It supports custom scrolling behavior, item spacing, and horizontal alignment.
 *
 * @param books The list of [Book] objects to display.
 * @param onBookClick Callback invoked when a book item is clicked.
 * @param scrollState The [LazyListState] to control the scrolling behavior of the list.
 * @param modifier Modifier for styling and positioning the book list.
 */
@Composable
fun BookList(
    books: List<Book>,
    onBookClick: (Book) -> Unit,
    scrollState: LazyListState = rememberLazyListState(),
    modifier: Modifier = Modifier,
) {
    // LazyColumn to display the list of books.
    LazyColumn(
        modifier = modifier, // Apply the provided modifier.
        state = scrollState, // Use the provided scroll state.
        verticalArrangement = Arrangement.spacedBy(12.dp), // Add vertical space between items.
        horizontalAlignment = Alignment.CenterHorizontally // Center items horizontally.
    ) {
        // Display each book in the list.
        items(
            items = books, // The list of books to display.
            key = { it.id } // Use the book's ID as the key for item identity.
        ) { book ->
            // Display the BookListItem for the current book.
            BookListItem(
                book = book, // The current book to display.
                modifier = Modifier
                    .widthIn(max = 700.dp) // Limit the maximum width of the item.
                    .fillMaxWidth() // Fill the available width.
                    .padding(horizontal = 16.dp), // Add horizontal padding.
                onClick = {
                    // Invoke the callback when the book is clicked.
                    onBookClick(book)
                }
            )
        }
    }
}