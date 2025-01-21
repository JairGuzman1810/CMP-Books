package org.app.books.book.presentation.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
 *
 * @property bookRepository The [BookRepository] used to access book data.
 * @property savedStateHandle The [SavedStateHandle] used to access navigation arguments.
 */
class BookDetailViewModel(
    private val bookRepository: BookRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    /**
     * The ID of the book to display details for.
     * Retrieved from the navigation arguments using the [SavedStateHandle].
     */
    private val bookId = savedStateHandle.toRoute<Route.BookDetail>().id

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
     *
     * When the flow starts, it fetches the book description and observes the favorite status.
     * It is shared using `SharingStarted.WhileSubscribed` to avoid unnecessary work when there are no active observers.
     */
    val state = _state
        .onStart {
            fetchBookDescription() // Fetches the book description when the flow starts.
            observeFavoriteStatus() // Observes the favorite status of the book when the flow starts.
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L), // Shares the flow while subscribed with a 5-second timeout.
            _state.value // The initial value of the state.
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
                    book = action.book // Updates the book in the state with the new selected book.
                )
            }

            /**
             * Handles the OnFavoriteClick action.
             *
             * If the book is currently a favorite, it removes it from favorites.
             * Otherwise, it adds the book to favorites.
             */
            is BookDetailAction.OnFavoriteClick -> {
                viewModelScope.launch {
                    if (state.value.isFavorite) {
                        // If the book is a favorite, remove it from favorites.
                        bookRepository.deleteFromFavorites(id = bookId)
                    } else {
                        // If the book is not a favorite, add it to favorites.
                        state.value.book?.let { book ->
                            bookRepository.markAsFavorite(book = book)
                        }
                    }
                }
            }

            /**
             * No action taken for other actions.
             */
            else -> Unit
        }
    }

    /**
     * Observes the favorite status of the book and updates the state accordingly.
     *
     * Launches a coroutine in the viewModelScope to collect the favorite status flow.
     * Updates the state with the new favorite status.
     */
    private fun observeFavoriteStatus() {
        bookRepository
            .isBookFavorite(id = bookId) // Gets the favorite status flow for the book.
            .onEach { isFavorite ->
                _state.update {
                    it.copy(
                        isFavorite = isFavorite // Updates the isFavorite property in the state.
                    )
                }
            }
            .launchIn(viewModelScope) // Launches the flow collection in the viewModelScope.
    }

    /**
     * Fetches the book description and updates the state accordingly.
     *
     * Launches a coroutine in the viewModelScope to fetch the book description.
     * Updates the state with the fetched description and sets isLoading to false.
     */
    private fun fetchBookDescription() {
        viewModelScope.launch {
            bookRepository.getBookDescription(
                bookWorkId = bookId // The ID of the book to fetch the description for.
            ).onSuccess { description ->
                _state.update {
                    it.copy(
                        book = it.book?.copy(
                            description = description // Updates the description of the book in the state.
                        ),
                        isLoading = false // Sets isLoading to false to indicate that the description has been fetched.
                    )
                }
            }
        }
    }
}