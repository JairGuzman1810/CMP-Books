package org.app.books.book.data.network

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
}