package org.app.books.book.data.repository

import androidx.sqlite.SQLiteException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.app.books.book.data.database.FavoriteBookDao
import org.app.books.book.data.mappers.toBook
import org.app.books.book.data.mappers.toBookEntity
import org.app.books.book.data.network.RemoteBookDataSource
import org.app.books.book.domain.model.Book
import org.app.books.book.domain.repository.BookRepository
import org.app.books.core.domain.DataError
import org.app.books.core.domain.EmptyResult
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
    private val remoteDataSource: RemoteBookDataSource,
    private val favoriteBookDao: FavoriteBookDao
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
        // Check if the book is in the local database (favorites).
        val localResult = favoriteBookDao.getFavoriteBook(bookWorkId)

        // If found in local database, return the description from there.
        return if (localResult == null) {
            // If not found locally, fetch from the remote data source.
            remoteDataSource
                .getBookDetails(bookWorkId)
                .map { it.description }
        } else {
            // Return the description from the local database.
            Result.Success(localResult.description)
        }
    }

    /**
     * Retrieves a flow of all favorite books.
     *
     * This function retrieves all favorite books from the local database and maps them to domain models.
     *
     * @return A [Flow] that emits a list of favorite [Book] objects.
     */
    override fun getFavoritesBooks(): Flow<List<Book>> {
        return favoriteBookDao
            .getFavoriteBooks()
            .map { bookEntities ->
                bookEntities.map { it.toBook() }
            }
    }

    /**
     * Checks if a book is marked as favorite.
     *
     * This function checks if a book with the given ID is present in the list of favorite books.
     *
     * @param id The ID of the book to check.
     * @return A [Flow] that emits a boolean value indicating whether the book is favorite.
     */
    override fun isBookFavorite(id: String): Flow<Boolean> {
        return favoriteBookDao
            .getFavoriteBooks()
            .map { bookEntities ->
                bookEntities.any { it.id == id }
            }
    }

    /**
     * Marks a book as favorite.
     *
     * This function inserts or updates the book in the local database to mark it as favorite.
     *
     * @param book The [Book] to mark as favorite.
     * @return An [EmptyResult] indicating success or a [DataError.Local] error.
     */
    override suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local> {
        return try {
            favoriteBookDao.upsert(book.toBookEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    /**
     * Deletes a book from favorites.
     *
     * This function deletes the book with the given ID from the local database to remove it from favorites.
     *
     * @param id The ID of the book to delete from favorites.
     */
    override suspend fun deleteFromFavorites(id: String) {
        favoriteBookDao.deleteFavoriteBook(id)
    }
}