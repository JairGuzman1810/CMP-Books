package org.app.books.book.data.network

import org.app.books.book.data.dto.BookWorkDto
import org.app.books.book.data.dto.SearchResponseDto
import org.app.books.core.domain.DataError
import org.app.books.core.domain.Result

/**
 * [RemoteBookDataSource] is an interface that defines the contract for accessing book data from a remote source.
 *
 * This interface abstracts the network layer, allowing the domain layer to interact with remote book data
 * without needing to know the specifics of how the data is fetched from the network.
 *
 * Implementations of this interface are responsible for handling the network communication and
 * converting the raw network responses into domain-specific data types.
 */
interface RemoteBookDataSource {

    /**
     * Searches for books based on a given [query] and an optional [resultLimit].
     *
     * This function performs a search operation against a remote data source.
     * It returns a [Result] object that encapsulates either a successful result containing a [SearchResponseDto] object,
     * or an error result of type [DataError.Remote] in case of a remote data access failure.
     *
     * @param query The search query string.
     * @param resultLimit The maximum number of results to return (optional).
     * @return A [Result] object that either contains a [SearchResponseDto] object or a [DataError.Remote] error.
     */
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    /**
     * Retrieves detailed information about a specific book work.
     *
     * This function fetches data from a remote source and returns a [Result] object.
     * The result can either be a [BookWorkDto] object, which contains the book details,
     * or a [DataError.Remote] error if the data retrieval fails.
     *
     * This is a **UI-oriented operation** because the result is directly used by the UI.
     *
     * @param bookWorkId The ID of the book work to retrieve details for.
     * @return A [Result] object that either contains a [BookWorkDto] object or a [DataError.Remote] error.
     */
    suspend fun getBookDetails(
        bookWorkId: String
    ): Result<BookWorkDto, DataError.Remote>
}