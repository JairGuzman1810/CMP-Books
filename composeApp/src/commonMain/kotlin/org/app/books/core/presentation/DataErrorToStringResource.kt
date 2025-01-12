package org.app.books.core.presentation

import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.error_disk_full
import books.composeapp.generated.resources.error_no_internet
import books.composeapp.generated.resources.error_request_timeout
import books.composeapp.generated.resources.error_serialization
import books.composeapp.generated.resources.error_too_many_requests
import books.composeapp.generated.resources.error_unknown
import org.app.books.core.domain.DataError

/**
 * Converts a [DataError] to a [UiText].
 *
 * This function maps different types of [DataError] to their corresponding [UiText] representations.
 * It uses a `when` expression to determine the appropriate string resource ID based on the type of [DataError].
 * This allows for the display of user-friendly error messages in the UI, based on the specific error that occurred.
 *
 * @receiver The [DataError] to convert.
 * @return A [UiText] object representing the error message.
 */
fun DataError.toUiText(): UiText {
    // Determines the appropriate string resource ID based on the type of DataError.
    val stringRes = when (this) {
        // Maps the DISK_FULL error to the corresponding string resource.
        DataError.Local.DISK_FULL -> Res.string.error_disk_full
        // Maps the UNKNOWN local error to the corresponding string resource.
        DataError.Local.UNKNOWN -> Res.string.error_unknown
        // Maps the REQUEST_TIMEOUT error to the corresponding string resource.
        DataError.Remote.REQUEST_TIMEOUT -> Res.string.error_request_timeout
        // Maps the TOO_MANY_REQUESTS error to the corresponding string resource.
        DataError.Remote.TOO_MANY_REQUESTS -> Res.string.error_too_many_requests
        // Maps the NO_INTERNET error to the corresponding string resource.
        DataError.Remote.NO_INTERNET -> Res.string.error_no_internet
        // Maps the SERVER error to the corresponding string resource.
        DataError.Remote.SERVER -> Res.string.error_unknown
        // Maps the SERIALIZATION error to the corresponding string resource.
        DataError.Remote.SERIALIZATION -> Res.string.error_serialization
        // Maps the UNKNOWN remote error to the corresponding string resource.
        DataError.Remote.UNKNOWN -> Res.string.error_unknown
    }

    // Returns a StringResourceId UiText with the determined string resource ID.
    return UiText.StringResourceId(stringRes)
}