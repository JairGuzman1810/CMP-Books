package org.app.books.book.domain.repository

import kotlinx.coroutines.flow.Flow
import org.app.books.book.domain.model.Book
import org.app.books.core.domain.DataError
import org.app.books.core.domain.EmptyResult
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

    /**
     * Retrieves the description of a book work.
     *
     * This function fetches the description of a book work from a remote data source.
     * It returns a [Result] object that encapsulates either a successful result containing a String,
     * or an error result of type [DataError] in case of a data access failure.
     *
     * @param bookWorkId The ID of the book work to retrieve the description for.
     * @return A [Result] object that either contains a String or a [DataError] error.
     */
    suspend fun getBookDescription(bookWorkId: String): Result<String?, DataError>

    /**
     * Retrieves a flow of all favorite books.
     *
     * This function returns a [Flow] that emits a list of [Book] objects representing the favorite books.
     * The flow will emit updates whenever the list of favorite books changes.
     *
     * @return A [Flow] that emits a list of favorite [Book] objects.
     */
    fun getFavoritesBooks(): Flow<List<Book>>

    /**
     * Checks if a book is marked as favorite.
     *
     * This function returns a [Flow] that emits a boolean value indicating whether the book with the given [id] is marked as favorite.
     * The flow will emit updates whenever the favorite status of the book changes.
     *
     * @param id The ID of the book to check.
     * @return A [Flow] that emits a boolean value indicating whether the book is favorite.
     */
    fun isBookFavorite(id: String): Flow<Boolean>

    /**
     * Marks a book as favorite.
     *
     * This function adds the given [book] to the list of favorite books.
     * It returns an [EmptyResult] that indicates whether the operation was successful or resulted in a [DataError.Local] error.
     *
     * @param book The [Book] to mark as favorite.
     * @return An [EmptyResult] indicating success or a [DataError.Local] error.
     */
    suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local>

    /**
     * Deletes a book from favorites.
     *
     * This function removes the book with the given [id] from the list of favorite books.
     *
     * @param id The ID of the book to delete from favorites.
     */
    suspend fun deleteFromFavorites(id: String)
}