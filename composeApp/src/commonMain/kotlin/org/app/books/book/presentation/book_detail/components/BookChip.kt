package org.app.books.book.presentation.book_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.app.books.core.presentation.LightBlue

/**
 * [ChipSize] is an enum class that defines the available sizes for the [BookChip] composable.
 *
 * It provides two options: [SMALL] and [REGULAR].
 */
enum class ChipSize {
    /**
     * Represents a small-sized chip.
     */
    SMALL,

    /**
     * Represents a regular-sized chip.
     */
    REGULAR
}

/**
 * [BookChip] is a composable function that creates a chip-like UI element.
 *
 * This composable is used to display information in a compact, visually distinct way,
 * such as the average rating, number of pages, or languages of a book.
 *
 * @param modifier The modifier to be applied to the chip.
 * @param size The size of the chip, which can be either [ChipSize.SMALL] or [ChipSize.REGULAR].
 * @param chipContent The content to be displayed inside the chip.
 */
@Composable
fun BookChip(
    modifier: Modifier = Modifier,
    size: ChipSize = ChipSize.REGULAR,
    chipContent: @Composable RowScope.() -> Unit
) {

    Box(
        modifier = modifier
            // Sets the minimum width of the chip based on the selected size.
            .widthIn(
                min = when (size) {
                    ChipSize.SMALL -> 50.dp
                    ChipSize.REGULAR -> 80.dp
                }
            )
            // Clips the chip to a rounded rectangle shape.
            .clip(RoundedCornerShape(10.dp))
            // Sets the background color of the chip.
            .background(LightBlue)
            // Adds padding around the content of the chip.
            .padding(
                vertical = 8.dp,
                horizontal = 12.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        // Displays the content of the chip in a row.
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            chipContent()
        }
    }
}