package org.app.books.core.data

import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import org.app.books.core.domain.DataError
import org.app.books.core.domain.Result
import kotlin.coroutines.coroutineContext

/**
 * [safeCall] safely executes a network request and handles potential exceptions.
 *
 * This suspend inline function executes a network request provided by the [execute] lambda.
 * It handles potential exceptions that might occur before, during, or after the request,
 * such as [UnresolvedAddressException] (no internet), [SerializationException], and other general exceptions.
 * It then uses [responseToResult] to convert the [HttpResponse] to a [Result] object.
 *
 * @param T The type of the expected response body.
 * @param execute A lambda that executes the network request and returns an [HttpResponse].
 * @return A [Result] object representing either a successful response with the parsed body or a [DataError.Remote].
 */
suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, DataError.Remote> {
    // Executes the network request and handles potential exceptions.
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        // Returns a [DataError.Remote.NO_INTERNET] error if the address cannot be resolved.
        return Result.Error(DataError.Remote.NO_INTERNET)
    } catch (e: SerializationException) {
        // Returns a [DataError.Remote.SERIALIZATION] error if there is an issue with serialization.
        return Result.Error(DataError.Remote.SERIALIZATION)
    } catch (e: Exception) {
        // Ensures the coroutine is still active before returning an error.
        coroutineContext.ensureActive()
        // Returns a [DataError.Remote.UNKNOWN] error for any other exceptions.
        return Result.Error(DataError.Remote.UNKNOWN)
    }

    // Converts the [HttpResponse] to a [Result] object.
    return responseToResult(response)
}

/**
 * [responseToResult] converts an [HttpResponse] to a [Result] object.
 *
 * This suspend inline function takes an [HttpResponse] from the Ktor client and converts it into a [Result] object,
 * handling different HTTP status codes and potential exceptions during response body transformation.
 *
 * @param T The type of the expected response body.
 * @param response The [HttpResponse] to convert.
 * @return A [Result] object representing either a successful response with the parsed body or a [DataError.Remote].
 */
suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, DataError.Remote> {
    // Checks the HTTP status code of the response.
    return when (response.status.value) {
        // Successful response (200-299).
        in 200..299 -> {
            // Attempts to parse the response body.
            try {
                // Returns a [Result.Success] result with the parsed body.
                Result.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                // Returns a [DataError.Remote.SERIALIZATION] error if the body cannot be parsed.
                Result.Error(DataError.Remote.SERIALIZATION)
            }
        }
        // Request timeout (408).
        408 -> Result.Error(DataError.Remote.REQUEST_TIMEOUT)
        // Too many requests (429).
        429 -> Result.Error(DataError.Remote.TOO_MANY_REQUESTS)
        // Server error (500-599).
        in 500..599 -> Result.Error(DataError.Remote.SERVER)
        // Unknown network error (any other status code).
        else -> Result.Error(DataError.Remote.UNKNOWN)
    }
}