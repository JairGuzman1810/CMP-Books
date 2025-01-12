package org.app.books.book.domain.repository

import org.app.books.book.domain.model.Book
import org.app.books.core.domain.DataError
import org.app.books.core.domain.Result

/**
 * [BookRepository] is an interface that defines the contract for interacting with book data.
 *
 * This interface abstracts the data access layer, allowing the domain layer to interact with book data
 * without needing to know the specifics of how the data is fetched or stored.
 *
 * The methods in this interface define the operations that can be performed on book data.
 */
interface BookRepository {

    /**
     * Searches for books based on a given query.
     *
     * This function performs a search operation to find books that match the provided [query].
     * It returns a [Result] object that encapsulates either a successful result containing a list of [Book] objects,
     * or an error result of type [DataError.Remote] in case of a remote data access failure.
     *
     * @param query The search query string.
     * @return A [Result] object that either contains a list of [Book] objects or a [DataError.Remote] error.
     */
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
}