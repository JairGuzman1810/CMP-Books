package org.app.books.book.presentation.book_list

import org.app.books.book.domain.Book

/**
 * Represents the possible actions that can be performed on the book list screen.
 *
 * This sealed interface defines the different types of events or intents that
 * can occur in the book list UI, such as changing the search query, clicking on a book,
 * or selecting a different tab.
 */
sealed interface BookListAction {

    /**
     * Represents an action where the search query has changed.
     *
     * @property query The new search query string.
     */
    data class OnSearchQueryChange(val query: String): BookListAction

    /**
     * Represents an action where a book has been clicked.
     *
     * @property book The book that was clicked.
     */
    data class OnBookClick(val book: Book): BookListAction

    /**
     * Represents an action where a tab has been selected.
     *
     * @property index The index of the selected tab.
     */
    data class OnTabSelected(val index: Int): BookListAction
}