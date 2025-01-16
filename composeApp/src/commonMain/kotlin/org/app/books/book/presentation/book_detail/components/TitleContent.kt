package org.app.books.book.presentation.book_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * [TitleContent] is a composable function that displays a title followed by custom content.
 *
 * This composable is designed to be used in conjunction with [BookChip] or other components
 * where you need to display a title above some related content.
 *
 * @param title The title text to be displayed.
 * @param modifier The modifier to be applied to the root layout.
 * @param content The composable content to be displayed below the title.
 */
@Composable
fun TitleContent(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally // Center the title and content horizontally.

    ) {
        Text(
            text = title, // Display the provided title.
            style = MaterialTheme.typography.titleSmall // Apply the titleSmall text style.
        )
        content() // Display the custom content provided to the TitleContent composable.
    }
}