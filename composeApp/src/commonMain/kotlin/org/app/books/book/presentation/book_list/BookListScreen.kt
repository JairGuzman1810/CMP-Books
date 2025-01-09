package org.app.books.book.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.favorites
import books.composeapp.generated.resources.no_favorite_books
import books.composeapp.generated.resources.no_search_results
import books.composeapp.generated.resources.search_results
import org.app.books.book.domain.Book
import org.app.books.book.presentation.book_list.components.BookList
import org.app.books.book.presentation.book_list.components.BookSearchBar
import org.app.books.core.presentation.DarkBlue
import org.app.books.core.presentation.DesertWhite
import org.app.books.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


/**
 * Composable function that serves as the root for the Book List screen.
 *
 * This composable is responsible for initializing the [BookListViewModel],
 * collecting the UI state, and passing it down to the [BookListScreen] composable.
 * It also handles the navigation action when a book is clicked.
 *
 * @param viewModel The BookListViewModel instance, provided by Koin.
 * @param onBookClick Callback invoked when a book item is clicked, providing the clicked [Book].
 */
@Composable
fun BookListScreenRoot(
    viewModel: BookListViewModel = koinViewModel(), // Get the ViewModel instance from Koin.
    onBookClick: (Book) -> Unit, // Callback for when a book is clicked.
) {
    // Collect the UI state from the ViewModel using collectAsStateWithLifecycle.
    val state by viewModel.state.collectAsStateWithLifecycle()

    // Display the BookListScreen, passing the state and action callback.
    BookListScreen(
        state = state, // Pass the current UI state to the screen.
        onAction = { action -> // Handle actions from the screen.

            // Handle different actions.
            when (action) {
                // If the action is a book click, invoke the onBookClick callback.
                is BookListAction.OnBookClick -> onBookClick(action.book)
                // For other actions, do nothing in this block.
                else -> Unit
            }

            // Delegate the action to the ViewModel to update the state.
            viewModel.onAction(action)
        }
    )
}

/**
 * Composable function that displays the main screen for the book list.
 *
 * This screen includes a search bar, a tab row for switching between search results and favorites,
 * and a pager to display the content for each tab. It also handles loading states, error messages,
 * and empty state scenarios.
 *
 * @param state The BookListState representing the current UI state.
 * @param onAction Callback invoked to handle user actions on the screen.
 */
@Composable
fun BookListScreen(
    state: BookListState,
    onAction: (BookListAction) -> Unit,
) {
    // Get the keyboard controller to hide the keyboard.
    val keyBoardController = LocalSoftwareKeyboardController.current

    // Remember the pager state for the tab content.
    val pagerState = rememberPagerState { 2 } // 2 pages: search results and favorites.

    // Remember the scroll states for the search results and favorites lists.
    val searchResultListState = rememberLazyListState()
    val favoriteResultListState = rememberLazyListState()

    // Scroll to the top of the search results list when the search results change.
    LaunchedEffect(key1 = state.searchResults) {
        searchResultListState.animateScrollToItem(0)
    }

    // Animate the pager to the selected tab when the selected tab index changes.
    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    // Update the selected tab index in the state when the pager page changes.
    LaunchedEffect(pagerState.currentPage) {
        onAction(BookListAction.OnTabSelected(pagerState.currentPage))
    }

    // Main column for the screen content.
    Column(
        modifier = Modifier
            .fillMaxSize() // Fill the entire screen.
            .background(DarkBlue) // Set the background color to DarkBlue.
            .statusBarsPadding(), // Add padding for the status bar.
        horizontalAlignment = Alignment.CenterHorizontally // Center content horizontally.
    ) {
        // Search bar component.
        BookSearchBar(
            searchQuery = state.searchQuery, // The current search query.
            onSearchQueryChange = {
                // Update the search query when it changes.
                onAction(BookListAction.OnSearchQueryChange(it))
            },
            onImeSearch = {
                // Hide the keyboard when the user performs a search action.
                keyBoardController?.hide()
            },
            modifier = Modifier
                .widthIn(
                    max = 400.dp
                ) // Limit the maximum width of the search bar.
                .fillMaxWidth() // Fill the available width.
                .padding(16.dp) // Add padding around the search bar.
        )

        // Surface for the tab content.
        Surface(
            modifier = Modifier
                .weight(1f) // Take up the remaining space.
                .fillMaxWidth(), // Fill the available width.
            color = DesertWhite, // Set the background color to DesertWhite.
            shape = RoundedCornerShape(
                topStart = 32.dp,
                topEnd = 32.dp
            ) // Set the shape to rounded corners at the top.
        ) {
            // Column for the tab row and pager.
            Column(
                horizontalAlignment = Alignment.CenterHorizontally // Center content horizontally.
            ) {
                // Tab row for switching between search results and favorites.
                TabRow(
                    selectedTabIndex = state.selectedTabIndex, // The currently selected tab index.
                    modifier = Modifier
                        .padding(vertical = 12.dp) // Add vertical padding.
                        .widthIn(max = 700.dp), // Limit the maximum width of the tab row.
                    containerColor = DesertWhite, // Set the background color to DesertWhite.
                    contentColor = SandYellow, // Set the content color to SandYellow.
                    indicator = { tabPositions ->
                        // Custom indicator for the selected tab.
                        TabRowDefaults.SecondaryIndicator(
                            color = SandYellow, // Set the indicator color to SandYellow.
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[state.selectedTabIndex]) // Position the indicator.
                        )
                    }
                ) {
                    // Search results tab.
                    Tab(
                        selected = state.selectedTabIndex == 0, // Check if this tab is selected.
                        onClick = {
                            // Update the selected tab index when this tab is clicked.
                            onAction(BookListAction.OnTabSelected(0))
                        },
                        modifier = Modifier.weight(1f), // Take up equal space.
                        selectedContentColor = SandYellow, // Set the color when selected.
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f) // Set the color when unselected.
                    ) {
                        // Text for the search results tab.
                        Text(
                            text = stringResource(Res.string.search_results), // Load the text from resources.
                            modifier = Modifier
                                .padding(vertical = 12.dp) // Add vertical padding.
                        )
                    }

                    // Favorites tab.
                    Tab(
                        selected = state.selectedTabIndex == 1, // Check if this tab is selected.
                        onClick = {
                            // Update the selected tab index when this tab is clicked.
                            onAction(BookListAction.OnTabSelected(1))
                        },
                        modifier = Modifier.weight(1f), // Take up equal space.
                        selectedContentColor = SandYellow, // Set the color when selected.
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f) // Set the color when unselected.
                    ) {
                        // Text for the favorites tab.
                        Text(
                            text = stringResource(Res.string.favorites), // Load the text from resources.
                            modifier = Modifier
                                .padding(vertical = 12.dp) // Add vertical padding.
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp)) // Add a small space below the tab row.

                // Horizontal pager for the tab content.
                HorizontalPager(
                    state = pagerState, // The pager state.
                    modifier = Modifier
                        .fillMaxWidth() // Fill the available width.
                        .weight(1f) // Take up the remaining space.
                ) { pageIndex ->
                    // Box to contain the content for each page.
                    Box(
                        modifier = Modifier
                            .fillMaxSize(), // Fill the entire page.
                        contentAlignment = Alignment.Center // Center content within the page.
                    ) {
                        // Content for each page based on the page index.
                        when (pageIndex) {
                            // Search results page.
                            0 -> {
                                // Show a loading indicator if the data is loading.
                                if (state.isLoading) {
                                    CircularProgressIndicator()
                                } else {
                                    // Handle different scenarios based on the state.
                                    when {
                                        state.errorMessage != null -> {
                                            // Display an error message if there is an error.
                                            Text(
                                                text = state.errorMessage.asString(), // Load the error message.
                                                textAlign = TextAlign.Center, // Center the text.
                                                style = MaterialTheme.typography.headlineSmall, // Set the text style.
                                                color = MaterialTheme.colorScheme.error // Set the text color to error color.
                                            )
                                        }

                                        state.searchResults.isEmpty() -> {
                                            // Display a message if there are no search results.
                                            Text(
                                                text = stringResource(Res.string.no_search_results), // Load the message from resources.
                                                textAlign = TextAlign.Center, // Center the text.
                                                style = MaterialTheme.typography.headlineSmall, // Set the text style.
                                                color = MaterialTheme.colorScheme.error // Set the text color to error color.
                                            )
                                        }

                                        else -> {
                                            // Display the list of search results.
                                            BookList(
                                                books = state.searchResults, // The list of search results.
                                                onBookClick = { book ->
                                                    // Handle the book click action.
                                                    onAction(BookListAction.OnBookClick(book))
                                                },
                                                modifier = Modifier.fillMaxSize(), // Fill the available space.
                                                scrollState = searchResultListState // Use the search results scroll state.
                                            )
                                        }
                                    }
                                }
                            }

                            // Favorites page.
                            1 -> {
                                // Check if the list of favorite books is empty.
                                if (state.favoriteBooks.isEmpty()) {
                                    // Display a message if there are no favorite books.
                                    Text(
                                        text = stringResource(Res.string.no_favorite_books), // Load the message from resources.
                                        textAlign = TextAlign.Center, // Center the text.
                                        style = MaterialTheme.typography.headlineSmall, // Set the text style.
                                    )
                                } else {
                                    // Display the list of favorite books.
                                    BookList(
                                        books = state.favoriteBooks, // The list of favorite books.
                                        onBookClick = { book ->
                                            // Handle the book click action.
                                            onAction(BookListAction.OnBookClick(book))
                                        },
                                        modifier = Modifier.fillMaxSize(), // Fill the available space.
                                        scrollState = favoriteResultListState // Use the favorites scroll state.
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}