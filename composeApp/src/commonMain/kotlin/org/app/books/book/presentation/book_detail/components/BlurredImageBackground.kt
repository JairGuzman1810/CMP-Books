package org.app.books.book.presentation.book_detail.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.book_cover
import books.composeapp.generated.resources.book_error_2
import books.composeapp.generated.resources.go_back
import books.composeapp.generated.resources.mark_as_favorite
import books.composeapp.generated.resources.remove_from_favorites
import coil3.compose.rememberAsyncImagePainter
import org.app.books.core.presentation.DarkBlue
import org.app.books.core.presentation.DesertWhite
import org.app.books.core.presentation.PulseAnimation
import org.app.books.core.presentation.SandYellow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * [BlurredImageBackground] is a composable function that displays a blurred background image
 * with a book cover in the foreground.
 *
 * This composable is used to create the header section of the BookDetail screen.
 * It handles the loading of the book cover image, displays a blurred version as the background,
 * and provides controls for navigating back and marking the book as a favorite.
 *
 * @param imageUrl The URL of the book's cover image.
 * @param isFavorite Indicates whether the book is marked as a favorite.
 * @param onFavoriteClick Callback function to be invoked when the favorite button is clicked.
 * @param onBackClick Callback function to be invoked when the back button is clicked.
 * @param modifier The modifier to be applied to the root layout.
 * @param content The composable content to be displayed below the book cover.
 */
@Composable
fun BlurredImageBackground(
    imageUrl: String?,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // State to track the image loading result.
    var imageLoadResult by remember {
        mutableStateOf<Result<Painter>?>(null)
    }
    // Load the image asynchronously using Coil.
    val painter = rememberAsyncImagePainter(
        model = imageUrl, // The URL of the book's cover image.
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
        }
    )

    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Top section with blurred background image.
            Box(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxSize()
                    .background(DarkBlue)
            ) {
                // Display the blurred image if it has loaded successfully.
                imageLoadResult?.getOrNull()?.let { painter ->

                    Image(
                        painter = painter,
                        contentDescription = stringResource(Res.string.book_cover),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .blur(20.dp) // Apply a blur effect to the background image.
                    )

                }

            }

            // Bottom section with white background.
            Box(
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxWidth()
                    .background(DesertWhite)
            )
        }

        // Back button.
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 16.dp, start = 16.dp)
                .statusBarsPadding() // Add padding for the status bar.
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(Res.string.go_back),
                tint = Color.White
            )
        }

        // Book cover and favorite button.
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Spacer to position the book cover below the blurred background.
            Spacer(modifier = Modifier.fillMaxHeight(0.15f))

            // Book cover card.
            ElevatedCard(
                modifier = Modifier
                    .height(230.dp)
                    .aspectRatio(2 / 3f), // Set the aspect ratio of the book cover.
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 15.dp // Add elevation to the card.
                )
            ) {
                // Animated content to show loading or image.
                AnimatedContent(
                    targetState = imageLoadResult,
                    label = ""
                ) { result ->

                    when (result) {
                        null -> Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            PulseAnimation(
                                modifier = Modifier
                                    .size(60.dp)
                            ) // Show a loading indicator (PulseAnimation) while the image is loading.
                        }
                        else -> {
                            Box {
                                // Display the image or an error image.
                                Image(
                                    painter = if (result.isSuccess) painter else {
                                        painterResource(Res.drawable.book_error_2) // Show an error image if the image failed to load.
                                    },
                                    contentDescription = stringResource(Res.string.book_cover), // Set the content description to the book's title.
                                    contentScale = if (result.isSuccess) {
                                        ContentScale.Crop // Crop the image if it loaded successfully.
                                    } else {
                                        ContentScale.Fit // Fit the error image if the image failed to load.
                                    },
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Transparent)
                                )
                                // Favorite button.
                                IconButton(
                                    onClick = onFavoriteClick, // Callback to handle the favorite click action.
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd) // Align the button to the bottom-end of the parent.
                                        .background(
                                            brush = Brush.radialGradient(
                                                // Apply a radial gradient background.
                                                colors = listOf(
                                                    SandYellow,
                                                    Color.Transparent // Gradient colors: SandYellow to transparent.
                                                ),
                                                radius = 70f, // Radius of the gradient.
                                            )
                                        )
                                ) {
                                    // Display the favorite icon.
                                    Icon(
                                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder, // Choose the icon based on the favorite state.
                                        tint = Color.Red, // Set the icon color to red.
                                        contentDescription = if (isFavorite) stringResource(Res.string.remove_from_favorites) else stringResource( // Set the content description based on the favorite state.
                                            Res.string.mark_as_favorite
                                        ),
                                    )

                                }
                            }
                        }
                    }
                }
            }
            // Display the custom content.
            content()
        }
    }
}