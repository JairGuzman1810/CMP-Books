package org.app.books.book.presentation.book_list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for the book list screen.
 *
 * This ViewModel manages the [BookListState] and handles [BookListAction]s
 * to update the state accordingly. It follows a unidirectional data flow pattern.
 */
class BookListViewModel: ViewModel() {

    /**
     * The internal mutable state flow for the book list state.
     */
    private val _state = MutableStateFlow(BookListState())

    /**
     * The public, read-only state flow for the book list state.
     */
    val state = _state.asStateFlow()

    /**
     * Handles actions performed on the book list screen.
     *
     * This function receives a BookListAction and updates the state
     * based on the type of action received.
     *
     * @param action The action to handle.
     */
    fun onAction(action: BookListAction) {
        when(action) {
            is BookListAction.OnBookClick -> {
                // TODO: Handle book click action
            }
            is BookListAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }
            is BookListAction.OnTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }
        }
    }
}