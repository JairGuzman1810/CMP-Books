package org.app.books.book.presentation.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.app.books.app.Route
import org.app.books.book.domain.repository.BookRepository
import org.app.books.core.domain.onSuccess

/**
 * [BookDetailViewModel] is a [ViewModel] that holds the state and handles actions for the BookDetail screen.
 *
 * It uses a [MutableStateFlow] to hold the [BookDetailState] and exposes it as a read-only StateFlow.
 *
 * The [onAction] function handles the different actions that can be triggered from the BookDetail screen.
 */
class BookDetailViewModel(
    private val bookRepository: BookRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val bookId = savedStateHandle.toRoute<Route.BookDetail>().id


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
    val state = _state
        .onStart {
            fetchBookDescription()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

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

    private fun fetchBookDescription() {
        viewModelScope.launch {
            bookRepository.getBookDescription(
                bookWorkId = bookId
            ).onSuccess { description ->
                _state.update {
                    it.copy(
                        book = it.book?.copy(
                            description = description
                        ),
                        isLoading = false
                    )
                }

            }

        }
    }
}