package org.app.books.book.presentation.book_list

import org.app.books.book.domain.model.Book
import org.app.books.core.presentation.UiText

/**
 * Represents the UI state for the book list screen.
 *
 * This data class holds all the necessary information to render the book list UI,
 * including the search query, search results, favorite books, loading state,
 * selected tab index, and any error messages.
 *
 * @property searchQuery The current search query entered by the user. Defaults to "Kotlin".
 * @property searchResults The list of books that match the current search query.
 * @property favoriteBooks The list of books that the user has marked as favorites.
 * @property isLoading Indicates whether the book list is currently loading data.
 * @property selectedTabIndex The index of the currently selected tab in the UI.
 * @property errorMessage An optional error message to display in the UI.
 */
data class BookListState(
    val searchQuery: String = "Kotlin",
    val searchResults: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = true,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)