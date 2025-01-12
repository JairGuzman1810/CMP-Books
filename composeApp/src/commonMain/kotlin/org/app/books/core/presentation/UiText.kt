package org.app.books.core.presentation

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * [UiText] is a sealed interface that represents text that can be displayed in the UI.
 *
 * This interface provides a way to handle text that can either be a dynamic string or a string resource ID.
 * It allows for flexibility in how text is managed and displayed in the UI, especially when dealing with
 * localized strings or text that is generated at runtime.
 */
sealed interface UiText {
    /**
     * [DynamicString] represents a string that is generated dynamically.
     *
     * This data class holds a string [value] that can be directly displayed in the UI.
     *
     * @property value The dynamic string value.
     */
    data class DynamicString(val value: String) : UiText

    /**
     * [StringResourceId] represents a string that is defined as a resource.
     *
     * This class holds a [StringResource] [id] and an optional array of [args] to format the string.
     * It is used to retrieve localized strings from the resources.
     *
     * @property id The [StringResource] ID of the string.
     * @property args An optional array of arguments to format the string.
     */
    class StringResourceId(
        val id: StringResource,
        val args: Array<Any> = arrayOf()
    ) : UiText

    /**
     * Converts the [UiText] to a [String].
     *
     * This function is a composable function that returns the string representation of the [UiText].
     * If the [UiText] is a [DynamicString], it returns the value.
     * If the [UiText] is a [StringResourceId], it retrieves the string from the resources using [stringResource]
     * and formats it with the provided args.
     *
     * @return The string representation of the [UiText].
     */
    @Composable
    fun asString(): String {
        // Determines the string representation based on the type of UiText.
        return when (this) {
            // Returns the dynamic string value.
            is DynamicString -> value
            // Retrieves the string from resources and formats it.
            is StringResourceId -> stringResource(resource = id, formatArgs = args)
        }
    }
}