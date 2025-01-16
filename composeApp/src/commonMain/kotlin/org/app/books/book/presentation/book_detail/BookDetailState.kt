package org.app.books.book.presentation.book_detail

import org.app.books.book.domain.model.Book

/**
 * [BookDetailState] represents the state of the BookDetail screen.
 *
 * It holds information about the current state of the screen, such as whether
 * it's loading, the favorite status of the book, and the selected book.
 */
data class BookDetailState(
    /**
     * Indicates whether the screen is currently loading data.
     */
    val isLoading: Boolean = true,
    /**
     * Indicates whether the displayed book is a favorite.
     */
    val isFavorite: Boolean = false,
    /**
     * The currently selected book, or null if no book is selected.
     */
    val book: Book? = null,
)