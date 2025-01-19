package org.app.books.book.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.app.books.book.data.dto.BookWorkDto
import org.app.books.book.data.dto.SearchResponseDto
import org.app.books.core.data.safeCall
import org.app.books.core.domain.DataError
import org.app.books.core.domain.Result

/**
 * The base URL for the Open Library API.
 */
private const val BASE_URL = "https://openlibrary.org/"

/**
 * [KtorRemoteBookDataSource] is an implementation of [RemoteBookDataSource] that uses Ktor's [HttpClient]
 * to fetch book data from the Open Library API.
 *
 * This class handles the network communication with the remote API and uses [safeCall] to safely execute
 * network requests and handle potential exceptions.
 *
 * @property httpClient The [HttpClient] instance used to make network requests.
 */
class KtorRemoteBookDataSource(
    private val httpClient: HttpClient,
) : RemoteBookDataSource {

    /**
     * Searches for books based on a given [query] and optional [resultLimit].
     *
     * This function performs a search operation against the Open Library API.
     * It constructs the request URL and parameters, then uses [safeCall] to execute the request.
     *
     * @param query The search query string.
     * @param resultLimit The maximum number of results to return (optional).
     * @return A [Result] object that either contains a [SearchResponseDto] with the search results or a [DataError.Remote] error.
     */
    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?
    ): Result<SearchResponseDto, DataError.Remote> {

        return safeCall<SearchResponseDto> {
            httpClient.get(
                urlString = "$BASE_URL/search.json"
            ) {
                // Adds the search query parameter.
                parameter("q", query)
                // Adds the result limit parameter if provided.
                parameter("limit", resultLimit)
                // Adds the language parameter to filter for English books.
                parameter("language", "eng")
                // Adds the fields parameter to specify the fields to include in the response.
                parameter(
                    "fields",
                    "key,title,author_name,author_key,cover_edition_key,cover_i,ratings_average,ratings_count,first_publish_year,language,number_of_pages_median,edition_count"
                )
            }
        }
    }

    /**
     * Retrieves detailed information about a specific book work.
     *
     * This function fetches data from a remote source and returns a [Result] object.
     * The result can either be a [BookWorkDto] object, which contains the book details,
     * or a [DataError.Remote] error if the data retrieval fails.
     *
     * @param bookWorkId The ID of the book work to retrieve details for.
     * @return A [Result] object that either contains a [BookWorkDto] object or a [DataError.Remote] error.
     */
    override suspend fun getBookDetails(bookWorkId: String): Result<BookWorkDto, DataError.Remote> {
        // Use safeCall to handle the network request and potential exceptions.
        return safeCall<BookWorkDto> {
            // Make a GET request to the Open Library API.
            httpClient.get(
                urlString = "$BASE_URL/works/$bookWorkId.json" // Construct the URL for the book details endpoint.
            )
        }
    }
}