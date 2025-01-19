package org.app.books.book.data.repository

import org.app.books.book.data.mappers.toBook
import org.app.books.book.data.network.RemoteBookDataSource
import org.app.books.book.domain.model.Book
import org.app.books.book.domain.repository.BookRepository
import org.app.books.core.domain.DataError
import org.app.books.core.domain.Result
import org.app.books.core.domain.map

/**
 * [BookRepositoryImpl] is an implementation of [BookRepository] that fetches book data from a remote data source.
 *
 * This class acts as a concrete implementation of the [BookRepository] interface,
 * providing the logic for how book data is retrieved and mapped from the remote data source.
 * It uses a [RemoteBookDataSource] to interact with the network layer and the [toBook] mapper
 * to convert data transfer objects (DTOs) to domain models.
 *
 * @property remoteDataSource The [RemoteBookDataSource] used to fetch book data from the network.
 */
class BookRepositoryImpl(
    private val remoteDataSource: RemoteBookDataSource
) : BookRepository {

    /**
     * Searches for books based on a given [query].
     *
     * This function delegates the search operation to the [remoteDataSource] and then maps the
     * resulting [SearchResponseDto] to a list of [Book] objects using the [toBook] mapper.
     *
     * @param query The search query string.
     * @return A [Result] object that either contains a list of [Book] objects or a [DataError.Remote] error.
     */
    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        // Delegates the search operation to the remote data source.
        return remoteDataSource.searchBooks(query)
            // Maps the Result<SearchResponseDto, DataError.Remote> to Result<List<Book>, DataError.Remote>.
            .map { dto ->
                // Maps the list of SearchedBookDto to a list of Book objects.
                dto.result.map {
                    // Converts each SearchedBookDto to a Book object using the toBook mapper.
                    it.toBook()
                }
            }
    }

    /**
     * Retrieves the description of a book work.
     *
     * This function fetches the description of a book work from a remote data source.
     * It returns a [Result] object that encapsulates either a successful result containing a String,
     * or an error result of type [DataError] in case of a data access failure.
     *
     * This is a **Domain-oriented operation** because it returns a String,
     * which are domain models.
     *
     * @param bookWorkId The ID of the book work to retrieve the description for.
     * @return A [Result] object that either contains a String or a [DataError] error.
     */
    override suspend fun getBookDescription(bookWorkId: String): Result<String?, DataError> {
        // Delegates the search operation to the remote data source.
        return remoteDataSource
            .getBookDetails(bookWorkId) // Get the book details from the remote data source.
            .map { it.description } // Map the result to the description.
    }
}