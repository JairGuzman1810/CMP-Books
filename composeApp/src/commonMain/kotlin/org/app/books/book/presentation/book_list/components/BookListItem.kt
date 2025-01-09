package org.app.books.book.presentation.book_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.book_error_2
import coil3.compose.rememberAsyncImagePainter
import org.app.books.book.domain.Book
import org.app.books.core.presentation.LightBlue
import org.app.books.core.presentation.SandYellow
import org.jetbrains.compose.resources.painterResource
import kotlin.math.round

/**
 * Composable function that displays a single book item in a list.
 *
 * This component shows a book's cover image, title, author, and average rating.
 * It also provides a clickable area to navigate to the book's details.
 *
 * @param book The [Book] object to display.
 * @param onClick Callback invoked when the book item is clicked.
 * @param modifier Modifier for styling and positioning the book item.
 */
@Composable
fun BookListItem(
    book: Book,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Surface to provide a background and shape to the book item.
    Surface(
        shape = RoundedCornerShape(32.dp), // Set the shape to rounded corners.
        modifier = modifier
            .clickable(onClick = onClick), // Make the surface clickable.
        color = LightBlue.copy(alpha = 0.2f) // Set the background color with some transparency.
    ) {
        // Row to arrange the book's content horizontally.
        Row(
            modifier = Modifier
                .padding(16.dp) // Add padding around the content.
                .fillMaxWidth() // Fill the available width.
                .height(IntrinsicSize.Min), // Set the height to the minimum intrinsic height.
            verticalAlignment = Alignment.CenterVertically, // Center the content vertically.
            horizontalArrangement = Arrangement.spacedBy(16.dp) // Add space between items.
        ) {
            // Box to contain the book's cover image.
            Box(
                modifier = Modifier
                    .height(100.dp), // Set the height of the image container.
                contentAlignment = Alignment.Center // Center the image within the box.
            ) {
                // State to track the image loading result.
                var imageLoadResult by remember {
                    mutableStateOf<Result<Painter>?>(null)
                }
                // Load the image asynchronously using Coil.
                val painter = rememberAsyncImagePainter(
                    model = book.imageUrl, // The URL of the book's cover image.
                    onSuccess = {
                        // Check if the image has a valid size
                        imageLoadResult =
                            if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                                Result.success(it.painter) // Image loaded successfully.
                            } else {
                                Result.failure(Exception("Invalid image size")) // Image has invalid size.
                            }
                    },
                    onError = {
                        // Handle image loading errors.
                        it.result.throwable.printStackTrace() // Print the error stack trace.
                        imageLoadResult = Result.failure(it.result.throwable) // Image failed to load.
                    }
                )

                // Display a loading indicator or the image based on the loading result.
                when (val result = imageLoadResult) {
                    null -> CircularProgressIndicator() // Show a loading indicator while the image is loading.
                    else -> {
                        // Display the image or an error image.
                        Image(
                            painter = if (result.isSuccess) painter else {
                                painterResource(Res.drawable.book_error_2) // Show an error image if the image failed to load.
                            },
                            contentDescription = book.title, // Set the content description to the book's title.
                            contentScale = if (result.isSuccess) {
                                ContentScale.Crop // Crop the image if it loaded successfully.
                            } else {
                                ContentScale.Fit // Fit the error image if the image failed to load.
                            },
                            modifier = Modifier
                                .aspectRatio(
                                    ratio = 0.65f, // Set the aspect ratio of the image.
                                    matchHeightConstraintsFirst = true // Match height constraints first.
                                )
                        )
                    }
                }
            }
            // Column to arrange the book's text information vertically.
            Column(
                modifier = Modifier
                    .fillMaxHeight() // Fill the available height.
                    .weight(1f), // Take up the remaining space.
                verticalArrangement = Arrangement.Center // Center the content vertically.
            ) {
                // Display the book's title.
                Text(
                    text = book.title, // Set the text to the book's title.
                    style = MaterialTheme.typography.titleMedium, // Set the text style.
                    maxLines = 2, // Limit the number of lines to 2.
                    overflow = TextOverflow.Ellipsis // Add an ellipsis if the text overflows.
                )
                // Display the book's author if available.
                book.authors.firstOrNull()?.let { authorName ->
                    Text(
                        text = authorName, // Set the text to the author's name.
                        style = MaterialTheme.typography.bodyLarge, // Set the text style.
                        maxLines = 1, // Limit the number of lines to 1.
                        overflow = TextOverflow.Ellipsis // Add an ellipsis if the text overflows.
                    )
                }
                // Display the book's average rating if available.
                book.averageRating?.let { rating ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically // Center the content vertically.
                    ) {
                        // Display the rating value.
                        Text(
                            text = "${round(rating * 10) / 10.0}", // Round the rating to one decimal place.
                            style = MaterialTheme.typography.bodyMedium // Set the text style.
                        )
                        // Display a star icon.
                        Icon(
                            imageVector = Icons.Default.Star, // Set the icon to a star.
                            contentDescription = null, // No content description needed for this icon.
                            tint = SandYellow // Set the icon color to SandYellow.
                        )
                    }
                }
            }
            // Icon to indicate that the item is clickable.
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, // Set the icon to a right arrow.
                contentDescription = null, // No content description needed for this icon.
                modifier = Modifier
                    .size(36.dp) // Set the size of the icon.
            )
        }
    }
}