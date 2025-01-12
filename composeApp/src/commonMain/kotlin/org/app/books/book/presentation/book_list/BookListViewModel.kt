package org.app.books.book.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.app.books.book.domain.model.Book
import org.app.books.book.domain.repository.BookRepository
import org.app.books.core.domain.onError
import org.app.books.core.domain.onSuccess
import org.app.books.core.presentation.toUiText

/**
 * [BookListViewModel] is the [ViewModel] for the [BookListScreen].
 *
 * This [ViewModel] manages the [BookListState] and handles [BookListAction]s
 * to update the state accordingly. It follows a unidirectional data flow pattern.
 * It interacts with the [BookRepository] to fetch book data.
 *
 * @property bookRepository The [BookRepository] used to fetch book data.
 */
class BookListViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

    /**
     * [cachedBooks] stores the list of books that have been previously fetched.
     *
     * This is used to avoid refetching the same data when the search query is cleared.
     */
    private var cachedBooks: List<Book> = emptyList()

    /**
     * [searchJob] holds the currently active search job.
     *
     * This is used to cancel any previous search jobs when a new search query is entered.
     */
    private var searchJob: Job? = null

    /**
     * [_state] is the internal mutable state flow for the book list state.
     *
     * This is used to update the state internally within the [ViewModel].
     */
    private val _state = MutableStateFlow(BookListState())

    /**
     * [state] is the public, read-only state flow for the book list state.
     *
     * This is exposed to the UI for observing the current state of the book list.
     * It starts by observing the search query if no books are cached.
     */
    val state = _state
        .onStart {
            // If there are no cached books, start observing the search query.
            if (cachedBooks.isEmpty()) {
                observeSearchQuery()
            }
        }
        // Converts the state flow to a shared state flow.
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    /**
     * Handles actions performed on the book list screen.
     *
     * This function receives a [BookListAction] and updates the state
     * based on the type of action received.
     *
     * @param action The [BookListAction] to handle.
     */
    fun onAction(action: BookListAction) {
        when (action) {
            // Handles the book click action.
            is BookListAction.OnBookClick -> {
                // TODO: Handle book click action
            }
            // Handles the search query change action.
            is BookListAction.OnSearchQueryChange -> {
                // Updates the state with the new search query.
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }
            // Handles the tab selection action.
            is BookListAction.OnTabSelected -> {
                // Updates the state with the new selected tab index.
                _state.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }
        }
    }

    /**
     * Observes the search query and performs a search when it changes.
     *
     * This function observes the [BookListState.searchQuery] from the [state], debounce the changes,
     * and then performs a search if the query is not blank and has at least 2 characters.
     */
    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state
            // Maps the state to the search query.
            .map { it.searchQuery }
            // Only emits when the search query changes.
            .distinctUntilChanged()
            // Debounce the search query changes by 500 milliseconds.
            .debounce(500L)
            // Performs a search when the query changes.
            .onEach { query ->
                when {
                    // If the query is blank, reset the search results to the cached books.
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                searchResults = cachedBooks
                            )
                        }
                    }
                    // If the query has at least 2 characters, perform a search.
                    query.length >= 2 -> {
                        // Cancel any previous search job.
                        searchJob?.cancel()
                        // Start a new search job.
                        searchJob = searchBooks(query)
                    }
                }
            }
            // Launches the flow in the viewModelScope.
            .launchIn(viewModelScope)
    }

    /**
     * Searches for books based on the given [query].
     *
     * This function updates the state to indicate that a search is in progress,
     * then calls the [bookRepository] to perform the search.
     * It updates the state with the search results or an error message.
     *
     * @param query The search query string.
     * @return A [Job] representing the search operation.
     */
    private fun searchBooks(query: String) = viewModelScope.launch {
        // Updates the state to indicate that a search is in progress.
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        // Performs the search operation.
        bookRepository
            .searchBooks(query)
            // Handles the successful search result.
            .onSuccess { searchResults ->
                // Updates the cached books with the new search results.
                cachedBooks = searchResults
                // Updates the state with the search results.
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        searchResults = searchResults
                    )
                }
            }
            // Handles the error case.
            .onError { error ->
                // Updates the state with the error message.
                _state.update {
                    it.copy(
                        searchResults = emptyList(),
                        isLoading = false,
                        errorMessage = error.toUiText()
                    )
                }
            }
    }
}