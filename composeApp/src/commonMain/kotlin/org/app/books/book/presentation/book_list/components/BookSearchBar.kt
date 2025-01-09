package org.app.books.book.presentation.book_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.close_hint
import books.composeapp.generated.resources.search_hint
import org.app.books.core.presentation.DarkBlue
import org.app.books.core.presentation.DesertWhite
import org.app.books.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource

/**
 * Composable function that renders a search bar for books.
 *
 * This component provides a customizable search bar with features like:
 * - A leading search icon.
 * - A trailing clear icon that appears when there is text in the search bar.
 * - Placeholder text.
 * - Customizable colors.
 * - IME search action support.
 * - Rounded corners.
 *
 * @param searchQuery The current text in the search bar.
 * @param onSearchQueryChange Callback invoked when the text in the search bar changes.
 * @param onImeSearch Callback invoked when the user performs a search action via the IME.
 * @param modifier Modifier for styling and positioning the search bar.
 */
@Composable
fun BookSearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onImeSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Provide custom colors for text selection handles and background.
    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = SandYellow, // Set the handle color to SandYellow.
            backgroundColor = SandYellow // Set the background color to SandYellow.
        )
    ) {
        // OutlinedTextField component for the search bar
        OutlinedTextField(
            value = searchQuery, // Set the current text value.
            onValueChange = onSearchQueryChange, // Callback for text changes.
            shape = RoundedCornerShape(100), // Set the shape to rounded corners.
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = DarkBlue, // Set the cursor color to DarkBlue.
                focusedBorderColor = SandYellow, // Set the border color when focused to SandYellow.
            ),
            placeholder = {
                // Placeholder text when the search bar is empty.
                Text(
                    text = stringResource(Res.string.search_hint) // Load the placeholder text from resources.
                )
            },
            leadingIcon = {
                // Leading icon (search icon).
                Icon(
                    imageVector = Icons.Default.Search, // Set the icon to the search icon.
                    contentDescription = null, // No content description needed for this icon.
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.66f) // Set the icon color with some transparency.
                )
            },
            singleLine = true, // Ensure the text field is a single line.
            keyboardActions = KeyboardActions(
                onSearch = {
                    // Callback for when the user performs a search action.
                    onImeSearch()
                }
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, // Set the keyboard type to text.
                imeAction = ImeAction.Search // Set the IME action to search.
            ),
            trailingIcon = {
                // Trailing icon (clear icon).
                AnimatedVisibility(
                    visible = searchQuery.isNotEmpty() // Only show the clear icon if there is text.
                ) {
                    IconButton(
                        onClick = {
                            // Clear the search query when the clear icon is clicked.
                            onSearchQueryChange("")
                        }
                    ) {
                        // Clear icon.
                        Icon(
                            imageVector = Icons.Default.Close, // Set the icon to the close icon.
                            contentDescription = stringResource(Res.string.close_hint), // Load the content description from resources.
                            tint = MaterialTheme.colorScheme.onSurface // Set the icon color.
                        )
                    }
                }
            },
            modifier = modifier
                .background(
                    shape = RoundedCornerShape(100), // Set the background shape to rounded corners.
                    color = DesertWhite // Set the background color to DesertWhite.
                )
                .minimumInteractiveComponentSize() // Ensure the component is large enough to be interactive.
        )
    }
}

