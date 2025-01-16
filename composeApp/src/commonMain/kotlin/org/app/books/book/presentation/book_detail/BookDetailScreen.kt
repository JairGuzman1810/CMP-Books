package org.app.books.book.presentation.book_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.description_unavailable
import books.composeapp.generated.resources.languages
import books.composeapp.generated.resources.pages
import books.composeapp.generated.resources.rating
import books.composeapp.generated.resources.synopsis
import org.app.books.book.presentation.book_detail.components.BlurredImageBackground
import org.app.books.book.presentation.book_detail.components.BookChip
import org.app.books.book.presentation.book_detail.components.ChipSize
import org.app.books.book.presentation.book_detail.components.TitleContent
import org.app.books.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
import kotlin.math.round

/**
 * [BookDetailScreenRoot] is the root composable for the Book Detail screen.
 *
 * It collects the [BookDetailState] from the [BookDetailViewModel] and passes it to the
 * [BookDetailScreen] composable. It also handles actions triggered from the screen,
 * such as navigating back or performing other actions.
 *
 * @param viewModel The [BookDetailViewModel] instance.
 * @param onBackClick Callback function to be invoked when the back button is clicked.
 */
@Composable
fun BookDetailScreenRoot(
    viewModel: BookDetailViewModel,
    onBackClick: () -> Unit,
) {

    // Collect the state from the ViewModel.
    val state by viewModel.state.collectAsStateWithLifecycle()

    BookDetailScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is BookDetailAction.OnBackClick -> onBackClick() // Handle back click action.
                else -> Unit // Handle other actions if needed.
            }
            viewModel.onAction(action) // Delegate the action to the ViewModel.
        },
    )
}

/**
 * [BookDetailScreen] is a composable function that displays the details of a book.
 *
 * It uses the [BlurredImageBackground] composable to display the book cover and
 * provides a scrollable layout for the book's details, such as the title, authors,
 * average rating, number of pages, languages, and synopsis.
 *
 * @param state The [BookDetailState] that holds the data to be displayed.
 * @param onAction Callback function to handle actions triggered from the screen.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun BookDetailScreen(
    state: BookDetailState,
    onAction: (BookDetailAction) -> Unit,
) {

    // Display the blurred image background with the book cover and controls.
    BlurredImageBackground(
        imageUrl = state.book?.imageUrl, // The URL of the book's cover image.
        isFavorite = state.isFavorite, // Whether the book is marked as a favorite.
        onFavoriteClick = {
            onAction(BookDetailAction.OnFavoriteClick) // Handle favorite click action.
        },
        onBackClick = {
            onAction(BookDetailAction.OnBackClick) // Handle back click action.
        },
        modifier = Modifier
            .fillMaxWidth() // Make the background fill the width of the screen.
    ) {
        // This lambda is the content that will be displayed below the blurred image.

        // Display the book details if a book is available.
        if (state.book != null) {

            Column(
                modifier = Modifier
                    .widthIn(max = 700.dp) // Limit the maximum width of the content.
                    .fillMaxWidth()
                    .padding(
                        vertical = 16.dp,
                        horizontal = 16.dp
                    )
                    .verticalScroll(rememberScrollState()), // Make the content scrollable.
                horizontalAlignment = Alignment.CenterHorizontally // Center the content horizontally.
            ) {

                // Book title.
                Text(
                    text = state.book.title,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )

                // Book authors.
                Text(
                    text = state.book.authors.joinToString(", "), // Display the authors separated by commas.
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )

                // Rating and page count.
                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp) // Add spacing between the rating and page count.
                ) {

                    // Average rating.
                    state.book.averageRating?.let { rating ->

                        TitleContent(
                            title = stringResource(Res.string.rating),
                        ) {
                            BookChip {
                                Text(
                                    text = "${round(rating * 10) / 10.0}", // Display the rating rounded to one decimal place.
                                )
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = SandYellow
                                )
                            }
                        }
                    }

                    // Page count.
                    state.book.numPages?.let { pageCount ->

                        TitleContent(
                            title = stringResource(Res.string.pages),
                        ) {
                            BookChip {
                                Text(
                                    text = pageCount.toString(), // Display the page count.
                                )
                            }
                        }
                    }

                }

                // Languages.
                if (state.book.languages.isNotEmpty()) {
                    TitleContent(
                        title = stringResource(Res.string.languages),
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                    ) {
                        FlowRow(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .wrapContentSize(Alignment.Center) // Center the languages within the FlowRow.
                        ) {

                            // Display each language as a chip.
                            state.book.languages.forEach { language ->

                                BookChip(
                                    size = ChipSize.SMALL,
                                    modifier = Modifier.padding(2.dp) // Add padding around each language chip.
                                ) {
                                    Text(
                                        text = language.uppercase(), // Display the language in uppercase.
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }

                            }

                        }

                    }
                }

                // Synopsis title.
                Text(
                    text = stringResource(Res.string.synopsis),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .align(Alignment.Start) // Align the synopsis title to the start.
                        .fillMaxWidth()
                        .padding(
                            top = 24.dp,
                            bottom = 8.dp
                        )
                )

                // Synopsis content.
                if (state.isLoading) {
                    CircularProgressIndicator() // Show a loading indicator while the synopsis is loading.
                } else {
                    // Display the synopsis text.
                    Text(
                        text = if (state.book.description.isNullOrBlank()) {
                            stringResource(Res.string.description_unavailable) // Display "description unavailable" if the description is empty.
                        } else {
                            state.book.description // Display the book's description.
                        },
                        style = MaterialTheme.typography.bodyMedium, // Apply the bodyMedium text style.
                        textAlign = TextAlign.Justify, // Justify the text alignment.
                        color = if (state.book.description.isNullOrBlank()) {
                            Color.Black.copy(alpha = 0.4f) // Dim the text if the description is unavailable.
                        } else {
                            Color.Black // Use black color if the description is available.
                        },
                        modifier = Modifier
                            .padding(vertical = 8.dp) // Add vertical padding.
                    )
                }
            }
        }
    }
}