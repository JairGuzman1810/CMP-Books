package org.app.books.book.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.app.books.book.domain.model.Book

/**
 * [SelectedBookViewModel] is a [ViewModel] that holds the currently selected [Book].
 *
 * This [ViewModel] is used to share the selected book between different screens
 * in the navigation graph. It uses a [MutableStateFlow] to hold the book and
 * exposes it as a read-only StateFlow.
 */
class SelectedBookViewModel : ViewModel() {

    /**
     * [_selectedBook] is a [MutableStateFlow] that holds the currently selected [Book].
     *
     * It is private to prevent external modification.
     */
    private val _selectedBook = MutableStateFlow<Book?>(null)

    /**
     * [selectedBook] is a read-only StateFlow that exposes the currently selected [Book].
     *
     * It is exposed as a StateFlow to allow UI components to observe changes.
     */
    val selectedBook = _selectedBook.asStateFlow()

    /**
     * Updates the [_selectedBook] with the given [book].
     *
     * @param book The [Book] that has been selected, or null if no book is selected.
     */
    fun onSelectedBook(book: Book?) {
        _selectedBook.value = book
    }
}