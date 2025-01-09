package org.app.books.book.presentation.book_list

import org.app.books.book.domain.Book
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
    val searchResults: List<Book> = books,
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)

/**
 * A sample list of books used for initial display or testing purposes.
 *
 * This list contains 100 dummy books with placeholder data.
 * In a real application, this would be replaced by data fetched from a repository.
 */
val books = (1..100).map {
    Book(
        id = it.toString(),
        title = "Book $it",
        imageUrl = "https://test.com",
        authors = listOf("Lorem author"),
        description = "Description $it",
        languages = emptyList(),
        firstPublishYear = null,
        averageRating = 4.67854,
        ratingCount = 5,
        numPages = 100,
        numEditions = 3
    )
}