package org.app.books.book.presentation.book_detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * [BookDetailViewModel] is a [ViewModel] that holds the state and handles actions for the BookDetail screen.
 *
 * It uses a [MutableStateFlow] to hold the [BookDetailState] and exposes it as a read-only StateFlow.
 *
 * The [onAction] function handles the different actions that can be triggered from the BookDetail screen.
 */
class BookDetailViewModel : ViewModel() {

    /**
     * [_state] is a [MutableStateFlow] that holds the current [BookDetailState].
     *
     * It is private to prevent external modification.
     */
    private val _state = MutableStateFlow(BookDetailState())

    /**
     * [state] is a read-only StateFlow that exposes the current [BookDetailState].
     *
     * It is exposed as a StateFlow to allow UI components to observe changes.
     */
    val state = _state.asStateFlow()

    /**
     * Handles the given [action] and updates the [BookDetailState] accordingly.
     *
     * @param action The action to handle.
     */
    fun onAction(action: BookDetailAction) {

        when (action) {
            /**
             * Updates the [BookDetailState] with the new selected book.
             */
            is BookDetailAction.OnSelectedBookChange -> {
                _state.value = _state.value.copy(
                    book = action.book
                )
            }

            /**
             * Handles the OnFavoriteClick action. (Not implemented yet)
             */
            is BookDetailAction.OnFavoriteClick -> {
                // TODO: Implement the logic for handling the favorite click.
            }

            /**
             * No action taken for other actions.
             */
            else -> Unit
        }

    }
}